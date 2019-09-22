package com.unq.viendasya.model

enum class MenuStatus {
    ACTIVE{
        override fun verify(rate: MutableList<Int>): MenuStatus {
            var newStatus = ACTIVE
            if (rate.size >= 20 && ranking(rate) <= 2.0)
                newStatus = CANCELED

            return newStatus
        }
    },
    EXPIRE{
        override fun verify(rate: MutableList<Int>): MenuStatus{
            return this
        }

    },
    CANCELED{
        override fun verify(rate: MutableList<Int>): MenuStatus{
            return this
        }

    };


    abstract fun verify(rate: MutableList<Int>): MenuStatus

    fun ranking(rate: MutableList<Int>): Double {
        return rate.sum().toDouble().div(rate.size.toDouble())
    }
}