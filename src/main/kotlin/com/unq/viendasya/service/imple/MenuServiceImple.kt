package com.unq.viendasya.service.imple

import com.unq.viendasya.controller.apiModels.MiniMenu
import com.unq.viendasya.exception.CurrencyMenuException
import com.unq.viendasya.model.Menu
import com.unq.viendasya.repository.MenuRepository
import com.unq.viendasya.service.MenuService
import org.joda.time.LocalDate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.lang.Exception
import java.util.*
import javax.transaction.Transactional

@Service
class MenuServiceImple(@Autowired private val  dao: MenuRepository) : MenuService {

    override fun createMenu(data: MiniMenu): Menu? {
        val menu = Menu.Builder().name(data.name)
                .cantMin(data.cantMin)
                .cantMax(data.cantMax)
                .cantMaxPeerDay(data.cantMaxPerDay)
                .deliveryTime(data.DeliveryTime)
                .deliveryValue(data.priceDelivery.toDouble())
                .expiration(LocalDate(data.dateEnd))
                .price(data.price.toDouble())
                .priceCantMin(data.priceMin.toDouble())
                .priceCantMax(data.priceMax.toDouble()).build()
        return dao.save(menu)
    }

    override fun findAll(): List<Menu> {
        return dao.findAll()
    }
    override fun findByQuery(query: String): List<Menu> {
        return dao.findByQuery("%${query.toUpperCase()}%")
    }

    override fun findById(idMenu: Int): Menu? {
        return dao.findByIdOrNull(idMenu)
    }

}