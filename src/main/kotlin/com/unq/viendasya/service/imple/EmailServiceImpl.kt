package com.unq.viendasya.service.imple

import com.unq.viendasya.service.EmailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

@Component
class EmailServiceImpl: EmailService {

    @Qualifier("getJavaMailSender")
    @Autowired
    private lateinit var emailSender: JavaMailSender

    override fun sendSimpleMessage(to: String, subject: String, text: String) {
        val message = SimpleMailMessage()
        message.setTo(to)
        message.setSubject(subject)
        message.setText(text)
        emailSender.send(message)
    }





}