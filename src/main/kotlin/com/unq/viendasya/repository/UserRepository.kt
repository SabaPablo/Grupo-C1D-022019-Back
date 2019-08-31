package com.unq.viendasya.repository

import com.unq.viendasya.model.UserModel
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserModel, Int> {
}