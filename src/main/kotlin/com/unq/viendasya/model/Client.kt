package com.unq.viendasya.model

import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

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

    fun createOrder(menu: Menu,cant : Int, date: LocalDate) {
        val order = Order.Builder(menu)
                .cant(cant)
                .date(date)
                .client(this)
                .build()
        orders.add(order)
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