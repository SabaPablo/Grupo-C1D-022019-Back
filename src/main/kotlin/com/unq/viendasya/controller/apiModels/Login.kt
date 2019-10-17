package com.unq.viendasya.controller.apiModels

data class Login(val mail: String,
                 var password: String) {
    var userId = -1
}