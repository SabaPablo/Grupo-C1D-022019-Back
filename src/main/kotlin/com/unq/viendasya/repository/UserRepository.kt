package com.unq.viendasya.repository

import com.unq.viendasya.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Int> {
    //@Query("SELECT u FROM User u WHERE u.status = 1")
    fun findByEmail(mail: String): User?
}