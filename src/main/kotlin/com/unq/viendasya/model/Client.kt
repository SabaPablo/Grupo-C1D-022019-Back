package com.unq.viendasya.model

import com.unq.viendasya.exception.AheadOfTimeException
import com.unq.viendasya.exception.InsufficientCreditException
import com.unq.viendasya.exception.MaxCantPeerDayException
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
        var creditAccount: Double) {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "client")
    val orders: MutableSet<Order> = mutableSetOf()

    fun createOrder(provider: Provider, menu: Menu, cant : Int, date: LocalDateTime) {
        if(onTimeForOffer(date)){
            if(provider.canOrderByCant(menu, cant, date)){
                if(canOrderByMoney(menu, cant)) {
                    this.deductMoney(menu.price * cant)
                    val order = Order.Builder(menu)
                            .cant(cant)
                            .date(date)
                            .client(this)
                            .build()
                    orders.add(order)
                    provider.addOrder(order, menu)
                }else{
                    throw InsufficientCreditException("Debería hacer una nueva carga de dinero para realizar la compra")
                }
            } else {
                throw MaxCantPeerDayException("El menu alcanzo el limite de contrataciones por dia")
            }
        }else {
            throw AheadOfTimeException("No se puede contratar servicio antes de las 48 hs")
        }


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
            var creditAccount: Double = 0.0) {

        fun name(name: String) = apply { this.name = name }
        fun email(email: String) = apply { this.email = email }
        fun phone(phone: String) = apply { this.phone = phone }
        fun location(location: String) = apply { this.location = location }
        fun creditAccount(creditAccount: Double) = apply { this.creditAccount = creditAccount }
        fun build() = Client(name, email, phone, location, creditAccount)

    }
}