package com.unq.viendasya.controller.apiModels

data class MiniOrder(val idMenu: Int,
                     val idClient: Int,
                     val deliveryDate: String,
                     val delivery: Boolean,
                     val cant: Int)