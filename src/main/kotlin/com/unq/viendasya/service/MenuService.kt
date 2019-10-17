package com.unq.viendasya.service

import com.unq.viendasya.controller.apiModels.MiniMenu
import com.unq.viendasya.model.Menu

interface MenuService {
    fun createMenu(menu: MiniMenu) : Menu?
    fun findById(idMenu: Int): Menu?
    fun findAll(): List<Menu>
}