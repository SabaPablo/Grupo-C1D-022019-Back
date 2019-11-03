package com.unq.viendasya.controller.apiModels

data class MiniMenu(val name: String,
                    val description: String,
                    //val category: List<Int>,
                    val priceDelivery: Int,
                    //val dateInit: String,
                    //val dateEnd: String,
                    //val turn: String,
                    //val DeliveryTime: String,
                    val urlImage: String,
                    val idProvider: Int,
                    val price: String,
                    val cantMin: Int,
                    val priceMin: String,
                    val cantMax: Int,
                    val priceMax: String,
                    val cantMaxPerDay: Int)