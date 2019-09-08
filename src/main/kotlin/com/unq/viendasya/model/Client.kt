package com.unq.viendasya.model

import com.unq.viendasya.exception.AheadOfTimeException
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
        var location: String) {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "client")
    val orders: MutableSet<Order> = mutableSetOf()

    fun createOrder(menu: Menu,cant : Int, date: LocalDateTime) {
        if(onTimeForOffer(date)){
            if(canOrderByCant(menu, cant, date)){
                val order = Order.Builder(menu)
                        .cant(cant)
                        .date(date)
                        .client(this)
                        .build()
                orders.add(order)
                menu.addOrder(order)
            } else {
                throw MaxCantPeerDayException("El menu alcanzo el limite de contrataciones por dia")
            }
        }else {
            throw AheadOfTimeException("No se puede contratar servicio antes de las 48 hs")
        }


    }

    private fun onTimeForOffer(date: LocalDateTime): Boolean {
        val diference = Hours.hoursBetween(LocalDateTime.now(),date)
        return diference.hours > 48
    }

    private fun canOrderByCant(menu: Menu, cant: Int, date: LocalDateTime): Boolean {
        return menu.cantMaxPeerDay > cant + menu.ordersOfDay(date)
    }


    data class Builder(
            var name: String = "",
            var email: String = "",
            var phone: String = "",
            var location: String = "") {

        fun name(name: String) = apply { this.name = name }
        fun email(email: String) = apply { this.email = email }
        fun phone(phone: String) = apply { this.phone = phone }
        fun location(location: String) = apply { this.location = location }
        fun build() = Client(name, email, phone, location)

    }
}