package com.unq.viendasya.controller.apiModels

import com.unq.viendasya.model.Order

data class AccountOrder(val order: Order, val credit: Double)