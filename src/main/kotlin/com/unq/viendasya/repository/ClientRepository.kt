package com.unq.viendasya.repository

import com.unq.viendasya.model.Client
import org.springframework.data.jpa.repository.JpaRepository

interface ClientRepository : JpaRepository<Client, Int> {
}