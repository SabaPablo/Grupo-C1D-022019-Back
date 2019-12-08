package com.unq.viendasya.controller

import com.unq.viendasya.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class LoginController(@Autowired val clientService: UserService ) {


}