package com.unq.viendasya.service

import com.unq.viendasya.controller.apiModels.MaxiOrder
import com.unq.viendasya.controller.apiModels.MiniOrder
import com.unq.viendasya.model.Order
import java.time.LocalDateTime

interface OrderService {
    fun createOrder(data: MiniOrder): Order?
    fun findByUserId(userId: Int): List<MaxiOrder>
    fun closeOrder(dateTime: LocalDateTime)
    fun valuateOrder(orderId: Int, valoration: Int): MaxiOrder?
}