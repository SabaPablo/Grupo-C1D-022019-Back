package com.unq.viendasya

import com.unq.viendasya.exception.AheadOfTimeException
import com.unq.viendasya.exception.InsufficientCreditException
import com.unq.viendasya.exception.MaxCantPeerDayException
import com.unq.viendasya.exception.OrderInHolidayException
import com.unq.viendasya.model.User
import com.unq.viendasya.model.Menu
import com.unq.viendasya.aux.impl.HolidayApiFakeAllHoliday
import com.unq.viendasya.aux.impl.HolidayApiFakeAllNonHoliday
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
        val client = User.Builder().holidaysAPI(HolidayApiFakeAllNonHoliday()).name("zaraza")
                .email("zaraza@mail.com")
                .phone("3344-4332").build()
        assert(client.name == "zaraza")
    }

    @Test
    fun createAClientAndBuildAMenu() {
        val client = User.Builder().name("zaraza")
                .holidaysAPI(HolidayApiFakeAllNonHoliday())
                .email("zaraza@mail.com")
                .phone("3344-4332").build()

        val menu: Menu = Menu.Builder().name("menu 11")
                .cantMin(5).cantMax(30).cantMaxPeerDay(50)
                .priceCantMin(210.0).expiration(LocalDate()).priceCantMax(190.0).build()

        val provider = User.Builder().build()
        client.createOrder(provider,menu,10, LocalDateTime.now().plusDays(3))
        Assert.assertEquals(client.orders.size, 1)
    }

    @Test(expected = MaxCantPeerDayException::class)
    fun createAClientAndBuildAMenuAndPassTheMaxCantPeerDay() {
        val client = User.Builder().name("zaraza")
                .holidaysAPI(HolidayApiFakeAllNonHoliday())
                .email("zaraza@mail.com")
                .phone("3344-4332").build()

        val menu: Menu = Menu.Builder().name("menu 11")
                .price(200.0).cantMin(5).cantMax(30).cantMaxPeerDay(50)
                .priceCantMin(210.0).expiration(LocalDate()).priceCantMax(190.0).build()

        val provider = User.Builder().build()

        client.createOrder(provider, menu,51, LocalDateTime.now().plusHours(96))
    }

    fun notPassTheMaxCantPeerDayForTwoClients() {
        val client = User.Builder().name("zaraza")
                .email("zaraza@mail.com")
                .holidaysAPI(HolidayApiFakeAllNonHoliday())
                .phone("3344-4332").build()

        val client2 = User.Builder().name("zaraza2")
                .holidaysAPI(HolidayApiFakeAllNonHoliday())
                .email("zaraza2@mail.com").build()

        val menu: Menu = Menu.Builder().name("menu 1")
                .price(200.0).cantMin(5).cantMax(30).cantMaxPeerDay(50)
                .priceCantMin(210.0).expiration(LocalDate()).priceCantMax(190.0).build()

        val provider = User.Builder().build()

        client.createOrder(provider, menu,10,LocalDateTime.now())


        client2.createOrder(provider, menu,30,LocalDateTime.now())

        Assert.assertEquals(client.orders.size, 1)
        Assert.assertEquals(client.orders.size, 1)
    }

    fun notPassTheMaxCantPeerDayForThreeClientsDaysDifferent() {
        val client = User.Builder().name("zaraza")
                .holidaysAPI(HolidayApiFakeAllNonHoliday())
                .email("zaraza@mail.com")
                .phone("3344-4332").build()

        val client2 = User.Builder().name("zaraza2")
                .holidaysAPI(HolidayApiFakeAllNonHoliday())
                .email("zaraza2@mail.com").build()

        val client3 = User.Builder().name("zaraza2")
                .holidaysAPI(HolidayApiFakeAllNonHoliday())
                .email("zaraza2@mail.com").build()
        val menu: Menu = Menu.Builder().name("menu 1")
                .price(200.0).cantMin(5).cantMax(30).cantMaxPeerDay(50)
                .priceCantMin(210.0).expiration(LocalDate()).priceCantMax(190.0).build()

        val provider = User.Builder().build()

        client.createOrder(provider, menu,10,LocalDateTime.now())

        client3.createOrder(provider, menu,30,LocalDateTime.now().plusDays(4))

        client2.createOrder(provider, menu,30,LocalDateTime.now())
        client2.createOrder(provider, menu,30,LocalDateTime.now().plusDays(5))


        Assert.assertEquals(client.orders.size, 1)
        Assert.assertEquals(client.orders.size, 1)
    }

    @Test(expected = MaxCantPeerDayException::class)
    fun passTheMaxCantPeerDayForTwoClients() {
        val client = User.Builder().name("zaraza")
                .holidaysAPI(HolidayApiFakeAllNonHoliday())
                .email("zaraza@mail.com")
                .phone("3344-4332").build()

        val client2 = User.Builder().name("zaraza2")
                .holidaysAPI(HolidayApiFakeAllNonHoliday())
                .email("zaraza2@mail.com").build()

        val menu: Menu = Menu.Builder().name("menu 1")
                .cantMin(5).cantMax(30).cantMaxPeerDay(50)
                .priceCantMin(210.0).expiration(LocalDate()).priceCantMax(190.0).build()

        val provider = User.Builder().build()

        client.createOrder(provider, menu,30,LocalDateTime.now().plusDays(4))

        client2.createOrder(provider, menu,30,LocalDateTime.now().plusDays(4))
    }

    @Test(expected = AheadOfTimeException::class)
    fun rejectOrderBecauseItIsAfter48hours() {
        val client = User.Builder().name("zaraza")
                .holidaysAPI(HolidayApiFakeAllNonHoliday())
                .email("zaraza@mail.com")
                .phone("3344-4332").build()

        val menu: Menu = Menu.Builder().name("menu 1")
                .price(200.0).cantMin(5).cantMax(30).cantMaxPeerDay(50)
                .priceCantMin(210.0).expiration(LocalDate()).priceCantMax(190.0).build()

        val provider = User.Builder().build()

        client.createOrder(provider, menu,3,LocalDateTime.now())
    }

    @Test
    fun clientCanChargeCreditInYourAccount(){
        val client = User.Builder().creditAccount(100.0)
                .holidaysAPI(HolidayApiFakeAllNonHoliday()).build()
        val menu = Menu.Builder().price(200.0).cantMin(5).cantMaxPeerDay(10).build()

        val provider = User.Builder().build()

        client.chargeCredit(500.0)
        client.createOrder(provider, menu,1, LocalDateTime.now().plusDays(3))

        Assert.assertEquals(client.orders.size, 1)
        Assert.assertEquals(400.0 ,client.accountBalance(), 0.01)
    }

    @Test
    fun clientCanPurchase2Menues(){
        val client = User.Builder()
                .holidaysAPI(HolidayApiFakeAllNonHoliday())
                .creditAccount(100.0).build()
        val menu = Menu.Builder().price(200.0).cantMin(5).cantMaxPeerDay(10).build()

        val provider = User.Builder().build()

        client.chargeCredit(500.0)
        client.createOrder(provider, menu,2, LocalDateTime.now().plusDays(3))

        Assert.assertEquals(client.orders.size, 1)
        Assert.assertEquals(200.0 ,client.accountBalance(), 0.01)
    }

    @Test(expected = InsufficientCreditException::class)
    fun clientCantPurchaseAMenue(){
        val client = User.Builder()
                .holidaysAPI(HolidayApiFakeAllNonHoliday())
                .creditAccount(100.0).build()
        val menu = Menu.Builder().price(200.0).cantMin(5).cantMaxPeerDay(10).build()

        val provider = User.Builder().build()

        client.chargeCredit(100.0)
        client.createOrder(provider, menu,2, LocalDateTime.now().plusDays(3))

        Assert.assertEquals(200.0 ,client.accountBalance(), 0.01)
    }

    @Test(expected = OrderInHolidayException::class)
    fun clientCantBuildAMenuBecauseIsHoliday() {
        val client = User.Builder().name("zaraza")
                .holidaysAPI(HolidayApiFakeAllHoliday())
                .email("zaraza@mail.com")
                .phone("3344-4332").build()

        val menu: Menu = Menu.Builder().name("menu 11")
                .cantMin(5).cantMax(30).cantMaxPeerDay(50)
                .priceCantMin(210.0).expiration(LocalDate()).priceCantMax(190.0).build()

        val provider = User.Builder().build()
        client.createOrder(provider,menu,10, LocalDateTime.now().plusDays(3))
        Assert.assertEquals(client.orders.size, 1)
    }

}
