package com.unq.viendasya.controller

import com.unq.viendasya.model.Client
import com.unq.viendasya.repository.ClientRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class ClientController(@Autowired private val  clientRepository : ClientRepository) {


    @GetMapping("/clients")
    fun getClients(pageable : Pageable) : Page<Client> {
        return clientRepository.findAll(pageable)
    }

    @PostMapping("/clients")
    fun createClient(@Valid @RequestBody user : Client) : Client {
        return clientRepository.save(user)
    }

    @GetMapping("/ping")
    fun test() : String {
        return "pong"
    }
}