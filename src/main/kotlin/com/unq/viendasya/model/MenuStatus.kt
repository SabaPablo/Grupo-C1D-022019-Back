package com.unq.viendasya.model

enum class MenuStatus {
    ACTIVE{
        override fun verify(rate: MutableList<Int>, provider: Client): MenuStatus {
            var newStatus = ACTIVE
            if (rate.size >= 20 && ranking(rate) <= 2.0)
                newStatus = CANCELED
                provider.verifyStaus()
                //provider.sendMailCancelMenu()
            //TODO: NO SE BIEN SI PONERLO ACA NO DEBERIA SER RESPONSABILIDAD DE ESTA CLASE EN
            // TODO CASO LE PASARIA EL MENU EN VEZ DE RATE

            return newStatus
        }
    },
    EXPIRE{
        override fun verify(rate: MutableList<Int>, provider: Client): MenuStatus{
            return this
        }

    },
    CANCELED{
        override fun verify(rate: MutableList<Int>, provider: Client): MenuStatus{
            return this
        }

    };


    abstract fun verify(rate: MutableList<Int>, provider: Client): MenuStatus

    fun ranking(rate: MutableList<Int>): Double {
        return rate.sum().toDouble().div(rate.size.toDouble())
    }
}