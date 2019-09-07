package com.unq.viendasya.model

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

    var id: Int = 0

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