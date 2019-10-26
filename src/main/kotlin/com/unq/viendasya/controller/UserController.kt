package com.unq.viendasya.controller

import com.unq.viendasya.controller.apiModels.MiniClient
import com.unq.viendasya.model.User
import com.unq.viendasya.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class UserController(@Autowired val clientService: UserService ) {

    @GetMapping("/clients")
    fun getClients(pageable: Pageable): Page<User> {
        return clientService.findAll(pageable)
    }
/*
    @GetMapping("/clients")
    fun getClient(@Valid @RequestParam (value = "id") id:Int): User? {
        return clientService.findById(id)
    }

 */

    @CrossOrigin
    @PostMapping("/clients")
    fun createClient(@Valid @RequestBody user : MiniClient): User {
        return clientService.createClient(user)
    }

}