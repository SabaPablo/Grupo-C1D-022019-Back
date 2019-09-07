package com.unq.viendasya

import com.unq.viendasya.model.Menu
import com.unq.viendasya.model.Provider
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

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

}
