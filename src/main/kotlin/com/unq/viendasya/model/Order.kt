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
        var client: Client,
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "provider_id")
        var provider: Provider
) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0


    fun menuPrice(): Double {
        var price = menu.price

        if( cant >= menu.cantMax){
            price = menu.priceCantMax
        }else{
            if(cant >= menu.cantMin){
                price = menu.priceCantMin
            }
        }
       return price
    }

    fun cant(): Int{
        return cant
    }

    fun close(){
        val priceDiff = this.cant * (menu.price - this.menuPrice())
        this.client.chargeCredit(priceDiff)
        //this.client.sendMailsConfirmation()
    }

    data class Builder(
            var menu: Menu,
            var cant: Int = 0,
            var date: LocalDateTime = LocalDateTime.now(),
            var client: Client = Client.Builder().build(),
            var provider: Provider = Provider.Builder().build()
    ) {
        fun menu(menu: Menu) = apply { this.menu = menu }
        fun cant(cant: Int) = apply { this.cant = cant }
        fun date(date: LocalDateTime) = apply { this.date = date}
        fun client(client: Client) = apply { this.client = client }
        fun provider(provider: Provider) = apply { this.provider = provider }
        fun build() = Order(menu, cant, date, client, provider)
    }
}
