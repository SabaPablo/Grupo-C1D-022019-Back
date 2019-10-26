package com.unq.viendasya.service

import com.unq.viendasya.controller.apiModels.MiniOrder
import com.unq.viendasya.model.Order

interface OrderService {
    fun createOrder(data: MiniOrder): Order?
}