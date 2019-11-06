package com.unq.viendasya.repository

import com.unq.viendasya.controller.apiModels.MaxiOrder
import com.unq.viendasya.model.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface OrderRepository : JpaRepository<Order, Int>{

    @Query("SELECT * FROM orders o WHERE o.client_id = :userId", nativeQuery = true)
    fun findByUserId( @Param("userId") userId: Int): List<Order>
}