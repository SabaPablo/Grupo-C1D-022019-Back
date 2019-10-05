package com.unq.viendasya.controller

import com.unq.viendasya.controller.apiModels.MiniMenu
import com.unq.viendasya.model.Menu
import com.unq.viendasya.repository.MenuRepository
import com.unq.viendasya.service.MenuService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class MenuController(@Autowired private val  menuService: MenuService) {

    @CrossOrigin
    @GetMapping("/menus")
    fun getMenus() : MutableList<Menu> {
        return menuService.findAll()
    }

    @PostMapping("/menus")
    fun createMenu(@Valid @RequestBody data : MiniMenu) : Menu {
        return menuService.createMenu(data)
    }

}