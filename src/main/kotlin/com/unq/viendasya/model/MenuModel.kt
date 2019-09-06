package com.unq.viendasya.model

import java.util.*

class MenuModel {
    var name: String = ""
    var description: String = ""
    val category: MutableList<ServiceCategory> = mutableListOf()
    var deliveryValue: Double = 0.0
    var validity: Date = Date()
    var expiration: Date = Date()
    //Turnos/Horarios de entrega/Envio
    var turn = ""
    var deliveryTime: String = ""
    var price :Double = 0.0
    var cantMin : Int = 0
    var priceCantMin: Double = 0.0
    var cantMax : Int = 0
    var priceCantMax: Double = 0.0
    var cantMaxPeerDay: Int = 0

}
