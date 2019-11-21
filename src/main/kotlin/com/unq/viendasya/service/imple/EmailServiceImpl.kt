package com.unq.viendasya.service.imple

import com.unq.viendasya.service.EmailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

@Component
class EmailServiceImpl() : EmailService {
    override fun sendSimpleMessage(to: String, subject: String, text: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}