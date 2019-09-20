package com.unq.viendasya.controller

import com.unq.viendasya.model.Client
import com.unq.viendasya.model.Menu
import com.unq.viendasya.repository.ClientRepository
import com.unq.viendasya.repository.MenuRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class MenuController(@Autowired private val  menuRepository : MenuRepository) {

    @CrossOrigin
    @GetMapping("/menus")
    fun getMenus() : MutableList<Menu> {
        return menuRepository.findAll()
    }

    @PostMapping("/menus")
    fun createClient(@Valid @RequestBody menu : Menu) : Menu {
        return menuRepository.save(menu)
    }

}