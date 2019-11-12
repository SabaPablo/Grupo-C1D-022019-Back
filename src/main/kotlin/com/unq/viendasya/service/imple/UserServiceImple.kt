package com.unq.viendasya.service.imple

import com.unq.viendasya.controller.apiModels.CreditRes
import com.unq.viendasya.controller.apiModels.MiniClient
import com.unq.viendasya.model.User
import com.unq.viendasya.repository.UserRepository
import com.unq.viendasya.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service

class UserServiceImple(@Autowired val dao: UserRepository): UserService {

    override fun findByMail(mail: String): User? {
        return dao.findByEmail(mail)
    }

    override fun findById(idClient: Int): User? {
        return dao.findByIdOrNull(idClient)
    }

    override fun createClient(data: MiniClient): User {
        val client = User.Builder().name("${data.name} ${data.lastName}" )
                .password(BCryptPasswordEncoder().encode(data.password))
                .location("${data.address}, ${data.city}, ${data.country}")
                .phone(data.phone)
                           .email(data.mail)
                           .build()
        return dao.save(client)
    }

    override fun findAll(pageable: Pageable): Page<User> {
        return dao.findAll(pageable)
    }

    override fun addCreditById(userId: Int, amount: Double): CreditRes {
        val user = findById(userId)
        val res = CreditRes(0,0.0)
        user?.let{
            it.chargeCredit(amount)
            dao.save(it)
            res.user_id = it.id
            res.amount = it.creditAccount
        }
        return res
    }

    override fun getAmountByUserId(userId: Int): Double {
        val user = findById(userId)
        return user?.creditAccount ?: 0.0
    }

}
