package com.unq.viendasya.controller

import com.unq.viendasya.controller.apiModels.MiniClient
import com.unq.viendasya.model.Client
import com.unq.viendasya.service.ClientService
import com.unq.viendasya.service.ClientServiceImple
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class ClientController(@Autowired val clientService: ClientService ) {

    @GetMapping("/clients")
    fun getClients(pageable : Pageable) : Page<Client> {
        return clientService.findAll(pageable)
    }

    @PostMapping("/clients")
    fun createClient(@Valid @RequestBody user : MiniClient) {
        clientService.createClient(user)
    }

    @GetMapping("/ping")
    fun test() : String {
        return "pong"
    }
}