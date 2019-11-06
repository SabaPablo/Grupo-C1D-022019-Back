package com.unq.viendasya.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.type.descriptor.java.DateTypeDescriptor.DATE_FORMAT
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
        @JsonFormat(pattern = DATE_FORMAT)
        var date: LocalDateTime,
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
        menu.provider.accountsStaiment(priceDiff)
        //this.client.sendMailsConfirmation()
    }

    data class Builder(
            var menu: Menu = Menu.Builder().build(),
            var cant: Int = 0,
            var date: LocalDateTime = LocalDateTime.now(),
            var client: User = User.Builder().build()
    ) {
        fun menu(menu: Menu) = apply { this.menu = menu }
        fun cant(cant: Int) = apply { this.cant = cant }
        fun date(date: LocalDateTime) = apply { this.date = date}
        fun client(client: User) = apply { this.client = client }
        fun build() = Order(menu, cant, date, client)
    }
}
