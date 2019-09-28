package com.unq.viendasya

import com.unq.viendasya.exception.CurrencyMenuException
import com.unq.viendasya.model.Menu
import com.unq.viendasya.model.Provider
import com.unq.viendasya.model.ProviderStatus
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest
class ProviderUnitTests {

    @Test
    fun createAProviderAndGetHisName() {
        val provider = Provider.Builder().name("Pizzarro").build()
        assert(provider.name == "Pizzarro")
        assert(provider.menues.isEmpty())
    }

    @Test
    fun createAProviderAndAddedAMenu() {
        val provider = Provider.Builder()
                .name("Pizzarro")
                .address("Calle Falsa 123")
                .description("Pizzeria con las mejores pizzas")
                .disponibility("L a V 4 a 18")
                .phone("555-5555")
                .location("Lat: 3.44, ")
                .build()


        val menu: Menu = Menu.Builder().name("Pizzas Individuales con Cerveza")
                .price(100.0).cantMin(5).cantMax(30).cantMaxPeerDay(50)
                .priceCantMin(110.0).priceCantMax(90.0).provider(provider).build()

        provider.addMenu(menu)

        assert(provider.menues.size == 1)
    }
    @Test
    fun createAProviderAndAddedTwoMenu() {
        val provider = Provider.Builder().name("Pizzarro")
                .address("Calle Falsa 123")
                .description("Pizzeria con las mejores pizzas")
                .disponibility("L a V 4 a 18")
                .phone("555-5555")
                .location("Lat: 3.44, ").build()


        val menu: Menu = Menu.Builder().name("Pizzas Individuales con Cerveza")
                .price(100.0).cantMin(5).cantMax(30).cantMaxPeerDay(50)
                .priceCantMin(110.0).priceCantMax(90.0).provider(provider).build()

        provider.addMenu(menu)

        val menu2: Menu = Menu.Builder().name("Pizzas con Gaseosa")
                .price(200.0).cantMin(5).cantMax(30).cantMaxPeerDay(50)
                .priceCantMin(210.0).priceCantMax(190.0).provider(provider).build()

        provider.addMenu(menu2)

        assert(provider.menues.size == 2)

    }
    @Test(expected = CurrencyMenuException::class)
    fun aProviderCantGetMoreTwentyMenuesCurrent() {
        val provider = Provider.Builder().name("Pizzarro")
                .address("Calle Falsa 123")
                .description("Pizzeria con las mejores pizzas")
                .disponibility("L a V 4 a 18")
                .phone("555-5555")
                .location("Lat: 3.44, ").build()

        for (i in 1..20) {
            val menu: Menu = Menu.Builder().name("menu $i")
                    .price(200.0).cantMin(5).cantMax(30).cantMaxPeerDay(50)
                    .priceCantMin(210.0).priceCantMax(190.0)
                    .provider(provider).expiration(LocalDate().plusDays(1)).build()

            provider.addMenu(menu)
        }

        val menu: Menu = Menu.Builder().name("menu 11")
                .price(200.0).cantMin(5).cantMax(30).cantMaxPeerDay(50)
                .priceCantMin(210.0).expiration(LocalDate()).provider(provider).priceCantMax(190.0).build()

        provider.addMenu(menu)

    }
    @Test
    fun aProviderCantGetMoreTwentyMenuesNotCurrent() {
        val provider = Provider.Builder().name("Pizzarro")
                .address("Calle Falsa 123")
                .description("Pizzeria con las mejores pizzas")
                .disponibility("L a V 4 a 18")
                .phone("555-5555")
                .location("Lat: 3.44, ").build()

        for (i in 1..20) {
            val menu: Menu = Menu.Builder().name("menu $i")
                    .price(200.0).cantMin(5).cantMax(30).cantMaxPeerDay(50)
                    .priceCantMin(210.0).priceCantMax(190.0).expiration(LocalDate().minusDays(1)).build()

            provider.addMenu(menu)
        }

        val menu: Menu = Menu.Builder().name("menu 11")
                .price(200.0).cantMin(5).cantMax(30).cantMaxPeerDay(50)
                .priceCantMin(210.0).expiration(LocalDate()).provider(provider)
                .priceCantMax(190.0).build()

        provider.addMenu(menu)
        Assert.assertEquals(21, provider.menues.size )
    }

    @Test
    fun aProviderCanWithdrawMoneyFromYourAccount(){
        val provider = Provider.Builder().build()
        provider.creditAccount = 1000.00

        provider.withdrawals(249.50)

        Assert.assertEquals(750.50, provider.creditAccount, 0.01)
    }

    @Test
    fun aProviderWithTwentyCanceledMenuesIsAlsoCanceled(){
        val provider = Provider.Builder().build()

        for (x in 0..8){
            val menu = Menu.Builder().expiration(LocalDate().plusDays(3)).build()
            for (y in 0..19) menu.addRanking(1)
            provider.addMenu(menu)
        }

        val menu10 = Menu.Builder().expiration(LocalDate().plusDays(3)).build()
        for (y in 0..18) menu10.addRanking(2)
        provider.addMenu(menu10)

        menu10.addRanking(1)

        Assert.assertEquals(ProviderStatus.CANCELED, provider.status)

    }
}