package com.unq.viendasya.model

import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "menues")
class Menu (
    var name: String,
    var description: String,
    var deliveryValue: Double,
    var validity: LocalDate,
    var expiration: LocalDate,
    //Turnos/Horarios de entrega/Envio
    var turn: String,
    var deliveryTime: String,
    var price :Double,
    var cantMin : Int,
    var priceCantMin: Double,
    var cantMax : Int,
    var priceCantMax: Double,
    var cantMaxPeerDay: Int,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "provider_id", referencedColumnName = "id")
    var provider: Provider) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "client")
    val orders: MutableSet<Order> = mutableSetOf()

    fun ordersOfDay(date: LocalDateTime): Int {
        val ordersOfDay = orders.filter { it.date.year == date.year &&
                it.date.monthOfYear == date.monthOfYear
                && it.date.dayOfMonth == date.dayOfMonth}
        var cantOrdersInTheDay = 0
        ordersOfDay.forEach { ood ->
            cantOrdersInTheDay += ood.cant
        }
        return cantOrdersInTheDay
    }

    fun addOrder(order: Order) {
        orders.add(order)
    }


    //val category: MutableList<ServiceCategory> = mutableListOf()

    data class Builder(
            var name: String = "",
            var description: String = "",
            var deliveryValue: Double = 0.0,
            var validity: LocalDate = LocalDate(),
            var expiration: LocalDate = LocalDate(),
            //Turnos/Horarios de entrega/Envio
            var turn: String = "",
            var deliveryTime: String = "",
            var price :Double = 0.0,
            var cantMin : Int = 0,
            var priceCantMin: Double = 0.0,
            var cantMax : Int = 0,
            var priceCantMax: Double = 0.0,
            var cantMaxPeerDay: Int = 0,
            var provider: Provider = Provider.Builder().build()
    ){

        //val category: MutableList<ServiceCategory> = mutableListOf()

        fun name(name: String) = apply { this.name = name }
        fun description(description: String) = apply { this.description= description}
        //fun category(category: ServiceCategory) = apply { this.category.add(category)}
        fun deliveryValue(deliveryValue: Double) = apply { this.deliveryValue= deliveryValue}
        fun validity(validity: LocalDate) = apply { this.validity= validity}
        fun expiration(expiration: LocalDate) = apply { this.expiration= expiration}
        fun turn(turn: String) = apply { this.turn= turn}
        fun deliveryTime(deliveryTime: String) = apply { this.deliveryTime= deliveryTime}
        fun price(price: Double) = apply { this.price= price}
        fun cantMin(cantMin: Int) = apply { this.cantMin= cantMin}
        fun priceCantMin(priceCantMin: Double) = apply { this.priceCantMin= priceCantMin}
        fun cantMax(cantMax: Int) = apply { this.cantMax= cantMax}
        fun priceCantMax(priceCantMax: Double) = apply { this.priceCantMax= priceCantMax}
        fun cantMaxPeerDay(cantMaxPeerDay: Int) = apply { this.cantMaxPeerDay= cantMaxPeerDay}
        fun provider(provider: Provider) = apply { this.provider= provider}


        fun build() = Menu(name, description, deliveryValue, validity, expiration, turn,
                deliveryTime, price, cantMin, priceCantMin, cantMax, priceCantMax, cantMaxPeerDay, provider)

    }

}
