package com.unq.viendasya.controller.apiModels

data class MiniMenu(val name: String,
                    val descripcion: String,
                    val Category: List<Int>,
                    val priceDelivery: String,
                    val dateInit: String,
                    val dateEnd: String,
                    val turn: String,
                    val DeliveryTime: String,

                    val price: String,
                    val cantMin: Int,
                    val priceMin: String,
                    val cantMax: Int,
                    val priceMax: String,
                    val cantMaxPerDay: Int)