package com.unq.viendasya

import com.unq.viendasya.exception.AheadOfTimeException
import com.unq.viendasya.exception.MaxCantPeerDayException
import com.unq.viendasya.model.Client
import com.unq.viendasya.model.Menu
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
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


        client.createOrder(menu,10, LocalDateTime.now().plusHours(49))
        Assert.assertEquals(client.orders.size, 1)
    }

    @Test(expected = MaxCantPeerDayException::class)
    fun createAClientAndBuildAMenuAndPassTheMaxCantPeerDay() {
        val client = Client.Builder().name("zaraza")
                .email("zaraza@mail.com")
                .phone("3344-4332").build()

        val menu: Menu = Menu.Builder().name("menu 11")
                .price(200.0).cantMin(5).cantMax(30).cantMaxPeerDay(50)
                .priceCantMin(210.0).expiration(LocalDate()).priceCantMax(190.0).build()


        client.createOrder(menu,51,LocalDateTime.now().plusHours(96))
    }

    fun notPassTheMaxCantPeerDayForTwoClients() {
        val client = Client.Builder().name("zaraza")
                .email("zaraza@mail.com")
                .phone("3344-4332").build()

        val client2 = Client.Builder().name("zaraza2")
                .email("zaraza2@mail.com").build()

        val menu: Menu = Menu.Builder().name("menu 1")
                .price(200.0).cantMin(5).cantMax(30).cantMaxPeerDay(50)
                .priceCantMin(210.0).expiration(LocalDate()).priceCantMax(190.0).build()

        client.createOrder(menu,10,LocalDateTime.now())


        client2.createOrder(menu,30,LocalDateTime.now())

        Assert.assertEquals(client.orders.size, 1)
        Assert.assertEquals(client.orders.size, 1)
    }

    fun notPassTheMaxCantPeerDayForThreeClientsDaysDifferent() {
        val client = Client.Builder().name("zaraza")
                .email("zaraza@mail.com")
                .phone("3344-4332").build()

        val client2 = Client.Builder().name("zaraza2")
                .email("zaraza2@mail.com").build()

        val client3 = Client.Builder().name("zaraza2")
                .email("zaraza2@mail.com").build()
        val menu: Menu = Menu.Builder().name("menu 1")
                .price(200.0).cantMin(5).cantMax(30).cantMaxPeerDay(50)
                .priceCantMin(210.0).expiration(LocalDate()).priceCantMax(190.0).build()

        client.createOrder(menu,10,LocalDateTime.now())

        client3.createOrder(menu,30,LocalDateTime.now().plusDays(4))

        client2.createOrder(menu,30,LocalDateTime.now())
        client2.createOrder(menu,30,LocalDateTime.now().plusDays(5))


        Assert.assertEquals(client.orders.size, 1)
        Assert.assertEquals(client.orders.size, 1)
    }

    @Test(expected = MaxCantPeerDayException::class)
    fun passTheMaxCantPeerDayForTwoClients() {
        val client = Client.Builder().name("zaraza")
                .email("zaraza@mail.com")
                .phone("3344-4332").build()

        val client2 = Client.Builder().name("zaraza2")
                .email("zaraza2@mail.com").build()

        val menu: Menu = Menu.Builder().name("menu 1")
                .price(200.0).cantMin(5).cantMax(30).cantMaxPeerDay(50)
                .priceCantMin(210.0).expiration(LocalDate()).priceCantMax(190.0).build()

        client.createOrder(menu,30,LocalDateTime.now().plusDays(4))


        client2.createOrder(menu,30,LocalDateTime.now().plusDays(4))
    }

    @Test(expected = AheadOfTimeException::class)
    fun rejectOrderBecauseItIsAfter48hours() {
        val client = Client.Builder().name("zaraza")
                .email("zaraza@mail.com")
                .phone("3344-4332").build()

        val menu: Menu = Menu.Builder().name("menu 1")
                .price(200.0).cantMin(5).cantMax(30).cantMaxPeerDay(50)
                .priceCantMin(210.0).expiration(LocalDate()).priceCantMax(190.0).build()


        client.createOrder(menu,3,LocalDateTime.now())
    }
}
