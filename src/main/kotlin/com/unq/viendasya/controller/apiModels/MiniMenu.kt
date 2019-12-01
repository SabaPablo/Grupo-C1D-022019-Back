package com.unq.viendasya.controller.apiModels

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class MiniMenu(
        @field:Size(min = 4, max = 30)
        val name: String,
        @field:Size(min = 20, max = 40)
        val description: String,
        @field:NotNull
        val category: MutableList<String>,
        @field:Min(10)
        @field:Max(40)
        val priceDelivery: Int,
                    val dateInit: String,
                    val dateEnd: String,
                    //val turn: String,
                    //val DeliveryTime: String,
                    val urlImage: String,
                    val idProvider: Int,
                    val price: String,
        @field:Min(10)
        @field:Max(70)
        val cantMin: Int,
        @field:Min(0)
        @field:Max(1000)
        val priceMin: String,
        @field:Min(40)
        @field:Max(150)
        val cantMax: Int,
        @field:Min(0)
        @field:Max(1000)
        val priceMax: String,
        @field:NotNull
        val cantMaxPerDay: Int)