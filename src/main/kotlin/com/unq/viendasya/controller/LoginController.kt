package com.unq.viendasya.controller

import com.unq.viendasya.controller.apiModels.Login
import com.unq.viendasya.service.ClientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class LoginController(@Autowired val clientService: ClientService ) {


    @CrossOrigin
    @PostMapping("/login")
    fun login(@Valid @RequestBody login : Login): Login {
        val user = clientService.findByMail(login.mail)
        user?.let{
            if(BCryptPasswordEncoder().matches(login.password, user.password)){
                login.userId = user.id
            }
        }
        login.password = " xD"
        return login
    }

}