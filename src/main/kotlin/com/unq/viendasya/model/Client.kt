package com.unq.viendasya.model

import com.unq.viendasya.exception.AheadOfTimeException
import com.unq.viendasya.exception.InsufficientCreditException
import com.unq.viendasya.exception.MaxCantPeerDayException
import com.unq.viendasya.exception.OrderInHolidayException
import com.unq.viendasya.service.RestHolidaysAPI
import com.unq.viendasya.service.imple.RestHolidaysAPIImple
import org.joda.time.Hours
import org.joda.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "users")
class Client(
        var name: String,
        var email: String,
        var phone: String,
        var location: String,
        var creditAccount: Double,
        @Transient
        var holidaysAPI: RestHolidaysAPI) {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "client")
    val orders: MutableSet<Order> = mutableSetOf()

    fun createOrder(provider: Provider, menu: Menu, cant : Int, date: LocalDateTime) {
        when{
            onTimeForOffer(date).not() -> throw AheadOfTimeException("No se puede contratar servicio antes de las 48 hs")
            provider.canOrderByCant(menu, cant, date).not() -> throw MaxCantPeerDayException("El menu alcanzo el limite de contrataciones por dia")
            canOrderByMoney(menu, cant).not() -> throw InsufficientCreditException("Debería hacer una nueva carga de dinero para realizar la compra")
            orderInHoliday(date) -> throw OrderInHolidayException("No se puede generar un pedido en un feriado")
            else -> {
                this.deductMoney(menu.price * cant)
                val order = Order.Builder(menu)
                        .cant(cant)
                        .date(date)
                        .client(this)
                        .build()
                orders.add(order)
                provider.addOrder(order, menu)
            }
        }
    }

    private fun orderInHoliday(date: LocalDateTime): Boolean {
        return holidaysAPI.isHoliday(date)
    }

    private fun canOrderByMoney(menu: Menu, cant: Int): Boolean {
        return menu.price * cant <= this.accountBalance()
    }

    private fun deductMoney(price: Double) {
        this.creditAccount = this.creditAccount - price
    }

    private fun onTimeForOffer(date: LocalDateTime): Boolean {
        val diference = Hours.hoursBetween(LocalDateTime.now(),date)
        return diference.hours > 48
    }


    fun chargeCredit(anAmmount: Double) {
        this.creditAccount = this.creditAccount + anAmmount
    }

    fun accountBalance(): Double {
        return this.creditAccount
    }


    data class Builder(
            var name: String = "",
            var email: String = "",
            var phone: String = "",
            var location: String = "",
            var holidaysAPI: RestHolidaysAPI = RestHolidaysAPIImple(),
            var creditAccount: Double = 0.0) {

        fun name(name: String) = apply { this.name = name }
        fun email(email: String) = apply { this.email = email }
        fun phone(phone: String) = apply { this.phone = phone }
        fun location(location: String) = apply { this.location = location }
        fun holidaysAPI(holidaysAPI: RestHolidaysAPI) = apply { this.holidaysAPI = holidaysAPI }
        fun creditAccount(creditAccount: Double) = apply { this.creditAccount = creditAccount }
        fun build() = Client(name, email, phone, location, creditAccount, holidaysAPI)

    }
}