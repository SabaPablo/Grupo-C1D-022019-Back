package com.unq.viendasya.controller

import com.unq.viendasya.controller.apiModels.MiniMenu
import com.unq.viendasya.model.Menu
import com.unq.viendasya.service.MenuService
import io.swagger.models.auth.In
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("/api")
class MenuController(@Autowired private val  menuService: MenuService) {

    @CrossOrigin
    @GetMapping("/menus")
    fun getMenus(@RequestParam(value = "pageNumber", defaultValue = "0") pageNumber: Int,
                 @RequestParam(value = "pageSize", defaultValue = "10") pageSize: Int) : Page<Menu> {
        val page: Pageable = PageRequest.of(pageNumber, pageSize)
        return menuService.findAll(page)
    }

    @CrossOrigin
    @GetMapping("/menus/{id}")
    fun getMenuById(@PathVariable("id") id: Int) : Menu? {
        return menuService.findById(id)
    }

    @CrossOrigin
    @GetMapping("/menus/query")
    fun getMenusbyQuery(@RequestParam(value = "query", defaultValue = "") query: String) : List<Menu> {
        return menuService.findByQuery(query)
    }
    @CrossOrigin
    @GetMapping("/menus/provider")
    fun getMenusbyQueryAndProviderId(@RequestParam(value = "providerId", defaultValue = "0") providerId: Int) : List<Menu> {
        return menuService.findByProviderId(providerId)
    }

    @CrossOrigin
    @PostMapping("/menus")
    fun createMenu(@Valid @RequestBody data : MiniMenu) : Menu? {
        return menuService.createMenu(data)
    }

}