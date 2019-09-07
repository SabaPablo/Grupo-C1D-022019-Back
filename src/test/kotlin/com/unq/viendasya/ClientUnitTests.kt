package com.unq.viendasya

import com.unq.viendasya.model.Client
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
}
