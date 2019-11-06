package com.unq.viendasya.controller.apiModels

class MaxiOrder(val menuName: String,
                val menuDescription: String,
                val menuCategory: MutableList<String>,
                val priceDelivery: Double,
                val urlImage: String,
                val nameProvider: String,
                val menuPrice: Double,
                val cant: Int
                ) {
}