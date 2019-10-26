package com.unq.viendasya.service

import com.unq.viendasya.controller.apiModels.MiniClient
import com.unq.viendasya.model.Client
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ClientService {
    fun createClient(data: MiniClient): Client
    fun findAll(pageable: Pageable): Page<Client>
    fun findById(idClient: Int): Client?
    fun findByMail(mail: String): Client?
}