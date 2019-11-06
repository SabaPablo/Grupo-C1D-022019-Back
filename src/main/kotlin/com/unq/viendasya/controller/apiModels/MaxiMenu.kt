package com.unq.viendasya.controller.apiModels

data class MaxiMenu(val name: String,
                    val description: String,
                    val category: MutableList<String>,
                    val priceDelivery: Int,
                    val dateInit: String,
                    val dateEnd: String,
                    //val turn: String,
                    //val DeliveryTime: String,
                    val rate: Double,
                    val urlImage: String,
                    val provider: MiniProvider,
                    val price: String,
                    val cantMin: Int,
                    val priceMin: String,
                    val cantMax: Int,
                    val priceMax: String,
                    val cantMaxPerDay: Int)