package com.unq.viendasya.controller

import com.unq.viendasya.controller.apiModels.MiniMenu
import com.unq.viendasya.model.Menu
import com.unq.viendasya.service.MenuService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class MenuController(@Autowired private val  menuService: MenuService) {

    @CrossOrigin
    @GetMapping("/menus")
    fun getMenus() : List<Menu> {
        return menuService.findAll()
    }

    @CrossOrigin
    @GetMapping("/menus/query")
    fun getMenusbyQuery(@RequestParam(value = "query", defaultValue = "") query: String) : List<Menu> {
        return menuService.findByQuery(query)
    }

    @PostMapping("/menus")
    fun createMenu(@Valid @RequestBody data : MiniMenu) : Menu? {
        return menuService.createMenu(data)
    }

}