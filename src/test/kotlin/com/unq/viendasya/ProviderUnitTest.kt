package com.unq.viendasya

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
        val provider = Provider.Builder().name("Pizzarro")
        assert(provider.name == "Pizzarro")
    }

}
