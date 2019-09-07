package com.unq.viendasya.repository

import com.unq.viendasya.model.ClientModel
import org.springframework.data.jpa.repository.JpaRepository

interface ClientRepository : JpaRepository<ClientModel, Int> {
}