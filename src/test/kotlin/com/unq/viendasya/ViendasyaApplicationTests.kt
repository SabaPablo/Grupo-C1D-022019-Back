package com.unq.viendasya

import com.unq.viendasya.model.Client
import com.unq.viendasya.model.Menu
import com.unq.viendasya.model.Order
import com.unq.viendasya.model.Provider
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class ViendasyaApplicationTests {

    @Test
    fun samePriceInOrderWithLessThanTheMinimumRequiredToChangePrice() {
        val menu = Menu.Builder().price(100.0).cantMin(3).priceCantMin(50.0).cantMax(5).build()

        val order = Order.Builder(menu).cant(1).build()

        Assert.assertEquals( 100.0, order.menuPrice(), 0.01)
    }

    @Test
    fun inOrderWithMoreThanTheMinimumRequiredToChangePriceReturnsThePriceOfTheMinimumQuantity() {
        val menu = Menu.Builder().price(100.0).cantMin(3).priceCantMin(50.0).cantMax(5).build()

        val order = Order.Builder(menu).cant(4).build()

        Assert.assertEquals(50.0, order.menuPrice(), 0.01)
    }

    @Test
    fun inOrderWithMoreThanTheMaximumRequiredToChangePriceReturnsThePriceOfTheMaximumQuantity() {
        val menu = Menu.Builder().price(100.0).cantMax(7).priceCantMax(30.0).build()

        val order = Order.Builder(menu).cant(8).build()

        Assert.assertEquals(30.0, order.menuPrice(),  0.01)
    }

    @Test
    fun whenCloseAnOrderTheSurplusWillBeReturnedToTheClient(){
        val menu = Menu.Builder()
                .price(100.0).cantMin(2)
                .priceCantMin(50.0)
                .cantMax(7)
                .priceCantMax(30.0)
                .cantMaxPeerDay(10)
                .build()

        val client = Client.Builder().creditAccount(350.0).build()

        val today = LocalDateTime.now()

        val provider = Provider.Builder().build()

        client.createOrder(provider, menu, 3, today.plusDays(4))


        Assert.assertEquals(50.0, client.accountBalance(),0.01)

        client.orders.last().close()

        Assert.assertEquals(200.0, client.accountBalance(),0.01)

    }

    @Test
    fun whenCloseAnOrdenTheProviderChargeAllCoustomerOrders(){
        val today = LocalDateTime.now()
        val anyClient = Client.Builder().build()
        val otherClient = Client.Builder().build()
        val menu = Menu.Builder()
                .cantMaxPeerDay(10)
                .price(250.0)
                .cantMin(3)
                .priceCantMin(150.0)
                .cantMax(10)
                .priceCantMax(100.0)
                .build()
        val provider = Provider.Builder().menues( mutableListOf(menu)).build()

        anyClient.chargeCredit(250.0)
        otherClient.chargeCredit(500.0)
        anyClient.createOrder(provider, menu,1, today.plusDays(3))
        otherClient.createOrder(provider, menu,2, today.plusDays(3))


        //Assert.assertEquals(100.0, anyClient.accountBalance(),0.01)
        //Assert.assertEquals(200.0,otherClient.accountBalance(), 0.01)
        Assert.assertEquals(750.0, provider.accountBalance(), 0.01)
    }

}
