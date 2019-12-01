package com.unq.viendasya.repository

import com.unq.viendasya.model.Order
import org.joda.time.LocalDateTime
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface OrderRepository : JpaRepository<Order, Int>{

    @Query("SELECT * FROM orders o WHERE o.client_id = :userId", nativeQuery = true)
    fun findByUserId( @Param("userId") userId: Int): List<Order>

    @Query("SELECT * FROM orders o WHERE status = 0 and o.date > :from AND o.date < :to", nativeQuery = true)
    fun getAllOrdersDueDate(@Param("from") from: Long, @Param("to") to: Long): List<Order>
}