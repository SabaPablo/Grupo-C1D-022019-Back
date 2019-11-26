package com.unq.viendasya.controller

import com.unq.viendasya.service.EmailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class MailController(@Autowired val emailService: EmailService) {

    @GetMapping("/mail")
    fun simpleMail(@RequestParam(value = "to", defaultValue = "") to: String,
                   @RequestParam(value = "subject", defaultValue = "") subject: String,
                   @RequestParam(value = "text", defaultValue = "") text: String) {
        return emailService.sendSimpleMessage(to,subject,text)
    }

}