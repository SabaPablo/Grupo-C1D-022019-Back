package com.unq.viendasya.model

import com.unq.viendasya.exception.*
import com.unq.viendasya.service.RestHolidaysAPI
import com.unq.viendasya.service.imple.RestHolidaysAPIImple
import org.joda.time.Hours
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "users")
class User(
        var name: String,
        var logo: String,
        var email: String,
        var phone: String,
        var webSite: String,
        var description: String,

        var location: String,
        var creditAccount: Double,

        //Horario y dia de atencion
        var disponibility: String,
        //Distancia maxima entrega
        var distanceDelivery: Int,
        var status: ProviderStatus,
        @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "provider")
        var menues: MutableList<Menu>,

        @Transient
        var holidaysAPI: RestHolidaysAPI,
        val password: String) {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "client")
    val orders: MutableSet<Order> = mutableSetOf()

    fun createOrder(provider: User, menu: Menu, cant : Int, date: LocalDateTime) {
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

    private fun getCantCurrentMenues(): Int {
        var count = 0
        menues.forEach{menu ->
            if(menu.expiration.isAfter(LocalDate.now())){
                count++
            }
        }
        return count
    }

    fun quatityOrdersOf(aMenu: Menu): Int {
        var ret = 0
        val order = aMenu.orders.find { order -> order.menu == aMenu  }
        if (order != null) {
            ret = order.cant()
        }
        return ret
    }

    fun addMenu(menu: Menu) {
        if(getCantCurrentMenues() >= 20){
            throw CurrencyMenuException("Superaste el maximo de menues vigentes")
        }else {
            menu.provider = this
            menues.add(menu)
        }

    }

    fun addOrder(order: Order, menu: Menu) {
        menu.orders.add(order)
        //creditAccount = creditAccount + (order.menu.standarPrice() * order.cant)
    }

    fun canOrderByCant(menu: Menu, cant: Int, date: LocalDateTime): Boolean {
        var canOrder = false

        if(menu.cantMaxPeerDay > cant){
            if( menu.orders.size !== 0 ){
                val order = menu.orders.filter { order -> order.menu == menu }
                //TODO CUANDO USEMOS BIEN TEMA FECHA HAYQ AGREGAR ESTO AL FILTER && order.date == date}.last()
                if (order.size !== 0){
                    canOrder = order.last().menu.cantMaxPeerDay > order.last().cant() + cant
                }
            }else{
                canOrder = true
            }
        }

        return canOrder
    }

    fun closeOrders(aMenu: Menu) {
        val quantityOfOrders = aMenu.orders.map { order -> order.cant() }.sum()

        aMenu.orders.map { order -> order.close(quantityOfOrders) }
    }

    fun accountsStaiment(priceDiff: Double) {
        creditAccount += priceDiff
    }

    fun withdrawals(quantity: Double) {
        creditAccount -= quantity
    }

    fun verifyStaus() {
        status = status.verify(menues)
    }



    data class Builder(
            var name: String = "",
            var email: String = "",
            var logo: String = "",
            var phone: String = "",
            var location: String = "",
            var address: String = "",
            var description: String = "",
            var webSite: String = "",
            //Horario y dia de atencion
            var disponibility: String = "",
            //Distancia maxima entrega
            var distanceDelivery: Int = 0,
            var creditAccount: Double = 0.0,
            var status: ProviderStatus = ProviderStatus.ACTIVE,
            var password: String = "",
            var holidaysAPI: RestHolidaysAPI = RestHolidaysAPIImple(),
            var menues :MutableList<Menu> = mutableListOf()) {

        fun name(name: String) = apply { this.name = name }
        fun email(email: String) = apply { this.email = email }
        fun logo(logo: String) = apply { this.logo = logo }
        fun phone(phone: String) = apply { this.phone = phone }
        fun location(location: String) = apply { this.location = location }
        fun address(address: String) = apply { this.address = address }
        fun description(description: String) = apply { this.description = description }
        fun webSite(webSite: String) = apply { this.webSite = webSite }
        fun disponibility(disponibility: String) = apply { this.disponibility = disponibility }
        fun status(status: ProviderStatus) = apply { this.status = status }
        fun password(password: String) = apply { this.password = password }

        fun holidaysAPI(holidaysAPI: RestHolidaysAPI) = apply { this.holidaysAPI = holidaysAPI }
        fun creditAccount(creditAccount: Double) = apply { this.creditAccount = creditAccount }
        fun build() = User(name,logo, email, phone,webSite,description, location, creditAccount,disponibility,distanceDelivery,status,menues, holidaysAPI, password)

    }
}