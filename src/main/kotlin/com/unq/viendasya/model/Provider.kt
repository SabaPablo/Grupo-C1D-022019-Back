package com.unq.viendasya.model

import com.unq.viendasya.exception.CurrencyMenuException
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "providers")
class Provider(
    var name: String,
    var logo: String,
    var location: String,
    var address: String,
    var description: String,
    var webSite: String,
    var mail: String,
    var phone: String,
    //Horario y dia de atencion
    var disponibility: String,
    //Distancia maxima entrega
    var distanceDelivery: Int,
    var creditAccount: Double,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "provider")
    var menues: MutableList<Menu>,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "provider")
    var orders: MutableList<Order>
    ) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    fun addMenu(menu: Menu) {
        if(getCantCurrentMenues() >= 20){
            throw CurrencyMenuException("Superaste el maximo de menues vigentes")
        }else {
            menues.add(menu)
        }

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
        val order = orders.find { order -> order.menu == aMenu  }
        if (order != null) {
            ret = order.cant()
        }
        return ret
    }

    fun addOrder(order: Order) {
        orders.add(order)
        creditAccount = creditAccount + (order.menu.standarPrice() * order.cant)
    }

    fun canOrderByCant(menu: Menu, cant: Int, date: LocalDateTime): Boolean {
        var canOrder = false

        if(menu.cantMaxPeerDay > cant){
            if( orders.size !== 0 ){
                val order = this.orders.filter { order -> order.menu == menu }
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

    fun accountBalance(): Double {
        return creditAccount
    }

    data class Builder(
            var name: String = "",
            var logo: String = "",
            var location: String = "",
            var address: String = "",
            var description: String = "",
            var webSite: String = "",
            var mail: String = "",
            var phone: String = "",
            //Horario y dia de atencion
            var disponibility: String = "",
            //Distancia maxima entrega
            var distanceDelivery: Int = 0,
            var creditAccount : Double = 0.0,
            var menues :MutableList<Menu> = mutableListOf(),
            var orders: MutableList<Order> = mutableListOf()) {

        fun name(name: String) = apply { this.name = name }
        fun logo(logo: String) = apply { this.logo = logo }
        fun location(location: String) = apply { this.location = location }
        fun address(address: String) = apply { this.address = address }
        fun description(description: String) = apply { this.description = description }
        fun webSite(webSite: String) = apply { this.webSite = webSite }
        fun mail(mail: String) = apply { this.mail = mail }
        fun phone(phone: String) = apply { this.phone = phone }
        fun disponibility(disponibility: String) = apply { this.disponibility = disponibility }
        fun creditAccount(creditAccount: Double) = apply { this.creditAccount = creditAccount }
        fun menues(menues: MutableList<Menu>) = apply { this.menues = menues }
        fun orders(orders: MutableList<Order>) = apply { this.orders = orders }
        fun build() = Provider(name, logo, location, address, description, webSite, mail, phone, disponibility, distanceDelivery, creditAccount ,menues, orders)
    }


}



