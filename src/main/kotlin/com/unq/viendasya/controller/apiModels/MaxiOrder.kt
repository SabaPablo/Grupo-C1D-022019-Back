package com.unq.viendasya.controller.apiModels

import com.unq.viendasya.model.OrderStatus

class MaxiOrder(val menuName: String,
                val orderId: Int,
                val menuDescription: String,
                val menuCategory: MutableList<String>,
                val priceDelivery: Double,
                val urlImage: String,
                val nameProvider: String,
                val menuPrice: Double,
                val status: OrderStatus,
                val cant: Int
                ) {
}
