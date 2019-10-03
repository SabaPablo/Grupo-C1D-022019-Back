package com.unq.viendasya.repository

import com.unq.viendasya.model.Client
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ClientRepository : JpaRepository<Client, Int> {
}