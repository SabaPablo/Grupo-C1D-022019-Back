package com.unq.viendasya

import com.unq.viendasya.exception.CurrencyMenuException
import com.unq.viendasya.model.Menu
import com.unq.viendasya.model.Provider
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest
class ProviderUnitTest {

    @Test
    fun createAProviderAndGetHisName() {
        val provider = Provider.Builder().name("Pizzarro").build()
        assert(provider.name == "Pizzarro")
        assert(provider.menues.isEmpty())
    }

    @Test
    fun createAProviderAndAddedAMenu() {
        val provider = Provider.Builder().name("Pizzarro")
                .address("Calle Falsa 123")
                .description("Pizzeria con las mejores pizzas")
                .disponibility("L a V 4 a 18")
                .phone("555-5555")
                .location("Lat: 3.44, ").build()


        val menu: Menu = Menu.Builder().name("Pizzas Individuales con Cerveza")
                .price(100.0).cantMin(5).cantMax(30).cantMaxPeerDay(50)
                .priceCantMin(110.0).priceCantMax(90.0).build()

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
                .priceCantMin(110.0).priceCantMax(90.0).build()

        provider.addMenu(menu)

        val menu2: Menu = Menu.Builder().name("Pizzas con Gaseosa")
                .price(200.0).cantMin(5).cantMax(30).cantMaxPeerDay(50)
                .priceCantMin(210.0).priceCantMax(190.0).build()

        provider.addMenu(menu2)

        assert(provider.menues.size == 2)

    }
    @Test(expected = CurrencyMenuException::class)
    fun aProviderCantGetMoreTenMenuesCurrent() {
        val provider = Provider.Builder().name("Pizzarro")
                .address("Calle Falsa 123")
                .description("Pizzeria con las mejores pizzas")
                .disponibility("L a V 4 a 18")
                .phone("555-5555")
                .location("Lat: 3.44, ").build()

        for (i in 0..10) {
            val menu: Menu = Menu.Builder().name("menu $i")
                    .price(200.0).cantMin(5).cantMax(30).cantMaxPeerDay(50)
                    .priceCantMin(210.0).priceCantMax(190.0).expiration(LocalDate().plusDays(1)).build()

            provider.addMenu(menu)
        }

        val menu: Menu = Menu.Builder().name("menu 11")
                .price(200.0).cantMin(5).cantMax(30).cantMaxPeerDay(50)
                .priceCantMin(210.0).expiration(LocalDate()).priceCantMax(190.0).build()

        provider.addMenu(menu)

    }
}