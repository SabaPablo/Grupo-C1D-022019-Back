package com.unq.viendasya.service.imple

import com.unq.viendasya.controller.apiModels.MiniClient
import com.unq.viendasya.model.Client
import com.unq.viendasya.repository.ClientRepository
import com.unq.viendasya.service.ClientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service

class ClientServiceImple(@Autowired val dao: ClientRepository): ClientService {
    override fun findByMail(mail: String): Client? {
        return dao.findByEmail(mail)
    }

    override fun findById(idClient: Int): Client? {
        return dao.findByIdOrNull(idClient)
    }

    override fun createClient(data: MiniClient): Client {
        val client = Client.Builder().name(data.name)
                .password(BCryptPasswordEncoder().encode(data.password))
                           .email(data.mail)
                           .build()
        return dao.save(client)
    }

    override fun findAll(pageable: Pageable): Page<Client> {
        return dao.findAll(pageable)
    }
}