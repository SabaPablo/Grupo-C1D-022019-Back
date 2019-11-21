package com.unq.viendasya.service


import org.springframework.mail.SimpleMailMessage

interface EmailService {
    fun sendSimpleMessage(to: String,
                          subject: String,
                          text: String)

}