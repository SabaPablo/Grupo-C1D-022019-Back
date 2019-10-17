package com.unq.viendasya.controller

import com.unq.viendasya.controller.apiModels.MiniOrder
import com.unq.viendasya.model.Order
import com.unq.viendasya.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class OrderController(@Autowired val orderService: OrderService) {

    @PostMapping("/orders")
    fun createOrders(@Valid @RequestBody data : MiniOrder): Order? {
        return orderService.createOrder(data)
    }

}