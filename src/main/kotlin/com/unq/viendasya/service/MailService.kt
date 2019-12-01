package com.unq.viendasya.service


import org.springframework.mail.SimpleMailMessage

interface MailService {
    fun sendSimpleMessage(to: String,
                          subject: String,
                          text: String)

}