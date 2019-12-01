package com.unq.viendasya

import com.unq.viendasya.model.Menu
import com.unq.viendasya.model.MenuStatus
import org.joda.time.LocalDate
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class MenuUnitTests {

    @Test
    fun rateMennu(){
        val menu = Menu.Builder().build()
        menu.rate(5)
        Assert.assertEquals(5.0, menu.ranking(), 0.01)

        menu.rate(4)
        Assert.assertEquals(4.5, menu.ranking(), 0.01)

        menu.rate(3)
        Assert.assertEquals(4.0, menu.ranking(), 0.01)

        menu.rate(1)
        menu.rate(1)
        menu.rate(4)
        Assert.assertEquals(3.0, menu.ranking(), 0.01)

        menu.rate(2)
        Assert.assertEquals(2.86, menu.ranking(), 0.01)

    }

    @Test
    fun menuRankingWithLessOf2PointsAndMoreOfTwentyCalificationIsCanceled(){
        val expirationDay = LocalDate.now().plusDays(3)
        val menu = Menu.Builder().expiration(expirationDay).build()

        for (x in 0..19) menu.addRanking(2)
        menu.addRanking(1)
        Assert.assertEquals( MenuStatus.CANCELED, menu.status)

    }
}