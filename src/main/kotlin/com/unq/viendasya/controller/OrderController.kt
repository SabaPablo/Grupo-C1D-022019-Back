package com.unq.viendasya.controller

import com.unq.viendasya.controller.apiModels.MaxiOrder
import com.unq.viendasya.controller.apiModels.MiniOrder
import com.unq.viendasya.model.Order
import com.unq.viendasya.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalDateTime
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class OrderController(@Autowired val orderService: OrderService) {

    @CrossOrigin
    @PostMapping("/orders")
    fun createOrders(@Valid @RequestBody data : MiniOrder): Order? {
        return orderService.createOrder(data)
    }


    @CrossOrigin
    @GetMapping("/orders")
    fun getOrdloersbyQueryAndUserId(@RequestParam(value = "userId", defaultValue = "0") userId: Int) : List<MaxiOrder> {
        return orderService.findByUserId(userId)
    }

    @CrossOrigin
    @GetMapping("/orders/valuate")
    fun setRate(@RequestParam(value = "orderId", defaultValue = "0") orderId: Int,
                @RequestParam(value = "valoration", defaultValue = "0") valoration: Int) : MaxiOrder? {
        return orderService.valuateOrder(orderId,valoration)
    }

    @CrossOrigin
    @GetMapping("/orders/close")
    fun closeOrders() {
        orderService.closeOrder(LocalDateTime.now())
    }

}