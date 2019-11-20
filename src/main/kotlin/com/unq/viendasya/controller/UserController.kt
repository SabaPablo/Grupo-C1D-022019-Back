package com.unq.viendasya.controller

import com.unq.viendasya.controller.apiModels.CreditRes
import com.unq.viendasya.controller.apiModels.MiniClient
import com.unq.viendasya.model.User
import com.unq.viendasya.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class UserController(@Autowired val clientService: UserService ) {

    @CrossOrigin
    @GetMapping("/clientsbymail/{mail}")
    fun getClientsByMail(@PathVariable("mail") mail: String): Int? {
        return clientService.findByMail(mail)?.id
    }

    @CrossOrigin
    @PostMapping("/clients")
    fun createClient(@Valid @RequestBody user : MiniClient): User {
        return clientService.createClient(user)
    }

    @CrossOrigin
    @PostMapping("/credit")
    fun createClient(@Valid @RequestBody credit : CreditRes): CreditRes {
        return clientService.addCreditById(credit.user_id, credit.amount)
    }

    @CrossOrigin
    @GetMapping("/credit")
    fun getAmountByUserId(@RequestParam(value = "user_id", defaultValue = "0") userId: Int) : Double {
        return clientService.getAmountByUserId(userId)
    }



}