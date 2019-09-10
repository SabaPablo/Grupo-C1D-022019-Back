package com.unq.viendasya.model

import org.joda.time.LocalDateTime
import org.joda.time.LocalTime
import javax.persistence.*

@Entity
@Table(name = "orders")
class Order (
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "menu_id", referencedColumnName = "id")
        var menu: Menu,
        var cant: Int,
        var date: LocalDateTime,
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "client_id")
        var client: Client
) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    data class Builder(
            var menu: Menu,
            var cant: Int = 0,
            var date: LocalDateTime = LocalDateTime.now(),
            var client: Client = Client.Builder().build()
    ) {
        fun menu(menu: Menu) = apply { this.menu = menu }
        fun cant(cant: Int) = apply { this.cant = cant }
        fun date(date: LocalDateTime) = apply { this.date = date}
        fun client(client: Client) = apply { this.client = client }
        fun build() = Order(menu, cant, date, client)

    }
}