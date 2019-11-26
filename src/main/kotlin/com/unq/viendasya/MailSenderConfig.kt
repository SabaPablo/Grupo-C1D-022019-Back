import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import java.util.*


@Configuration
class MailSenderConfig {
    @Bean
    fun getJavaMailSender(): JavaMailSender {
        val mailSender = JavaMailSenderImpl()
        mailSender.host = "smtp.gmail.com"
        mailSender.port = 587
        mailSender.username = "testmailsenderdesaunq@gmail.com"
        mailSender.password = "t3stm41ls3nd3r"
        val props: Properties = mailSender.javaMailProperties
        props["mail.transport.protocol"] = "smtp"
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.starttls.enable"] = "true"
        props["mail.debug"] = "true"
        return mailSender
    }

    @Bean
    fun templateSimpleMessage(): SimpleMailMessage {
        val message = SimpleMailMessage()
        message.setText("This is the test email template for your email:\n%s\n")
        return message
    }
}