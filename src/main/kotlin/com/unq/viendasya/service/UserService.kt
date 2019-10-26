package com.unq.viendasya.service

import com.unq.viendasya.controller.apiModels.MiniClient
import com.unq.viendasya.model.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UserService {
    fun createClient(data: MiniClient): User
    fun findAll(pageable: Pageable): Page<User>
    fun findById(idClient: Int): User?
    fun findByMail(mail: String): User?
}