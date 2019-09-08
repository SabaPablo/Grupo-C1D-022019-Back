package com.unq.viendasya.model

import org.joda.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "orders")
class Order (
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "menu_id", referencedColumnName = "id")
        var menu: Menu,
        var cant: Int,
        var date: LocalDate,
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
            var date: LocalDate = LocalDate.now(),
            var client: Client = Client.Builder().build()
    ) {
        fun menu(menu: Menu) = apply { this.menu = menu }
        fun cant(cant: Int) = apply { this.cant = cant }
        fun date(date: LocalDate) = apply { this.date = date }
        fun client(client: Client) = apply { this.client = client }
        fun build() = Order(menu, cant, date, client)

    }
}
