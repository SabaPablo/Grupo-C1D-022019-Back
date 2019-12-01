package com.unq.viendasya.service

import com.unq.viendasya.controller.apiModels.MiniMenu
import com.unq.viendasya.model.Menu
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface MenuService {
    fun createMenu(menu: MiniMenu) : Menu?
    fun findById(idMenu: Int): Menu?
    fun findAll(pageable: Pageable): Page<Menu>
    fun findByQuery(query: String): List<Menu>
    fun findByProviderId(providerId: Int): List<Menu>
}