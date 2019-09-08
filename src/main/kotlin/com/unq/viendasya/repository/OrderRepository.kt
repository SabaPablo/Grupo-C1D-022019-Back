package com.unq.viendasya.repository

import com.unq.viendasya.model.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, Int>