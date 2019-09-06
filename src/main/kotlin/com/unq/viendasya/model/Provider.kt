package com.unq.viendasya.model

class Provider(
    var name: String,
    var logo: String,
    var location: String,
    var address: String,
    var description: String,
    var webSite: String,
    var mail: String,
    var phone: String,
    //Horario y dia de atencion
    var disponibility: String,
    //Distancia maxima entrega
    var distanceDelivery: Int,
    var menues :MutableList<MenuModel>
    ) {

    data class Builder(
            var name: String = "",
            var logo: String = "",
            var location: String = "",
            var address: String = "",
            var description: String = "",
            var webSite: String = "",
            var mail: String = "",
            var phone: String = "",
            //Horario y dia de atencion
            var disponibility: String = "",
            //Distancia maxima entrega
            var distanceDelivery: Int = 0,
            var menues :MutableList<MenuModel> = mutableListOf()) {

        fun name(name: String) = apply { this.name = name }
        fun logo(logo: String) = apply { this.logo = logo }
        fun location(location: String) = apply { this.location = location }
        fun address(address: String) = apply { this.address = address }
        fun description(description: String) = apply { this.description = description }
        fun webSite(webSite: String) = apply { this.webSite = webSite }
        fun mail(mail: String) = apply { this.mail = mail }
        fun phone(phone: String) = apply { this.phone = phone }
        fun disponibility(disponibility: String) = apply { this.disponibility = disponibility }
        fun menues(menues: MutableList<MenuModel>) = apply { this.menues = menues }
        fun build() = Provider(name, logo, location, address, description, webSite, mail, phone, disponibility, distanceDelivery, menues)
    }


}


