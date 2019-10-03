package com.unq.viendasya.service

import com.unq.viendasya.controller.apiModels.MiniClient
import com.unq.viendasya.model.Client
import com.unq.viendasya.repository.ClientRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service

class ClientServiceImple(@Autowired val dao: ClientRepository): ClientService {

    override fun createClient(data: MiniClient) {
        val client = Client.Builder().name(data.name)
                           .email(data.mail)
                           .build()
        dao.save(client)
    }

    override fun findAll(pageable: Pageable): Page<Client> {
        return dao.findAll(pageable)
    }
}