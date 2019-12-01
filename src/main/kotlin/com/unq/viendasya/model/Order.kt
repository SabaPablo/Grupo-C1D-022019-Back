package com.unq.viendasya.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.joda.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "orders")
class Order (
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "menu_id")
        @JsonIgnore
        var menu: Menu,
        var cant: Int,
        var date: Long,
        var rate: Int,
        var status: OrderStatus,
        var delivery: Boolean,
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "client_id")
        @JsonIgnore
        var client: User
) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0


    fun menuPrice(totalNumbersOfOrders: Int): Double {
        var price = menu.price

        if( totalNumbersOfOrders >= menu.cantMax){
            price = menu.priceCantMax
        }else{
            if(totalNumbersOfOrders >= menu.cantMin){
                price = menu.priceCantMin
            }
        }
       return price
    }

    fun cant(): Int{
        return cant
    }

    fun close(totalNumbersOfOrders: Int){
        val priceDiff = cant * (menu.price - this.menuPrice(totalNumbersOfOrders))
        client.chargeCredit(priceDiff)
        status = OrderStatus.Accept
        menu.provider.accountsStaiment(priceDiff)

    }

    data class Builder(
            var menu: Menu = Menu.Builder().build(),
            var cant: Int = 0,
            var date: Long = LocalDateTime.now().toDate().time,
            var status: OrderStatus = OrderStatus.Pending,
            var rate: Int = 0,
            var delivery: Boolean = false,
            var client: User = User.Builder().build()
    ) {
        fun menu(menu: Menu) = apply { this.menu = menu }
        fun cant(cant: Int) = apply { this.cant = cant }
        fun rate(rate: Int) = apply { this.rate = rate }
        fun status(status: OrderStatus) = apply { this.status = status }
        fun date(date: LocalDateTime) = apply { this.date = date.toDate().time}
        fun client(client: User) = apply { this.client = client }
        fun delivery(delivery: Boolean) = apply { this.delivery = delivery }
        fun build() = Order(menu, cant, date,rate, status, delivery, client)
    }
}
