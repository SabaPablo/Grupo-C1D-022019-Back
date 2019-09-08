package com.unq.viendasya

import com.unq.viendasya.model.Client
import com.unq.viendasya.model.Menu
import org.joda.time.LocalDate
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class ClientUnitTests {

    @Test
    fun createAClientAndGetHisName() {
        val client = Client.Builder().name("zaraza")
                .email("zaraza@mail.com")
                .phone("3344-4332").build()
        assert(client.name == "zaraza")
    }

    @Test
    fun createAClientAndBuildAMenu() {
        val client = Client.Builder().name("zaraza")
                .email("zaraza@mail.com")
                .phone("3344-4332").build()

        val menu: Menu = Menu.Builder().name("menu 11")
                .price(200.0).cantMin(5).cantMax(30).cantMaxPeerDay(50)
                .priceCantMin(210.0).expiration(LocalDate()).priceCantMax(190.0).build()


        client.createOrder(menu,10,java.time.LocalDate.now())
        Assert.assertEquals(client.orders.size, 1)
    }
}
