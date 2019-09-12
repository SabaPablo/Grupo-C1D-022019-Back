package com.unq.viendasya

import com.unq.viendasya.model.Client
import com.unq.viendasya.model.Menu
import com.unq.viendasya.model.Order
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
        val client = Client.Builder().build()
        val order = Order.Builder(menu).cant(1).build()

        Assert.assertEquals( 100.0, order.menuPrice(), 0.01)
    }

    @Test
    fun inOrderWithMoreThanTheMinimumRequiredToChangePriceReturnsThePriceOfTheMinimumQuantity() {
        val menu = Menu.Builder().price(100.0).cantMin(3).priceCantMin(50.0).cantMax(5).build()
        val client = Client.Builder().build()
        val order = Order.Builder(menu).cant(4).build()

        Assert.assertEquals(50.0, order.menuPrice(), 0.01)
    }

    @Test
    fun inOrderWithMoreThanTheMaximumRequiredToChangePriceReturnsThePriceOfTheMaximumQuantity() {
        val menu = Menu.Builder().price(100.0).cantMax(7).priceCantMax(30.0).build()
        val client = Client.Builder().build()
        val order = Order.Builder(menu).cant(8).build()

        Assert.assertEquals(30.0, order.menuPrice(),  0.01)
    }
}
