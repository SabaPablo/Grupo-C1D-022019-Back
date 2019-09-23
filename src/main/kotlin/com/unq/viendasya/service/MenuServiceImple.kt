package com.unq.viendasya.service

import com.unq.viendasya.exception.CurrencyMenuException
import com.unq.viendasya.model.Menu
import com.unq.viendasya.repository.MenuRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.Exception
import javax.transaction.Transactional

@Service
class MenuServiceImple(@Autowired private val  menuRepository: MenuRepository) :MenuService {

    @Transactional
    override fun createMenu(menu: Menu) {
        try{
            menu.rate = mutableListOf()
            menu.provider.addMenu(menu)

            menuRepository.save(menu)
        } catch (e: CurrencyMenuException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }
}