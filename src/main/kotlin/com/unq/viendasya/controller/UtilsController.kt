package com.unq.viendasya.controller

import com.unq.viendasya.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class UtilsController(@Autowired val clientService: UserService ) {

    @GetMapping("/ping")
    fun test() : String {
        return "pong"
    }

}