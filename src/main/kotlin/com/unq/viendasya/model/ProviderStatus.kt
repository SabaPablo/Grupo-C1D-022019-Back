package com.unq.viendasya.model

enum class ProviderStatus {
    ACTIVE{
        override fun verify(menus: MutableList<Menu>): ProviderStatus {
            var newStatus = ACTIVE
            if (menus.filter { menu -> menu.status == MenuStatus.CANCELED  }.size == 9)
                newStatus = CANCELED

            return newStatus
        }
    },

    CANCELED{
        override fun verify(menus: MutableList<Menu>): ProviderStatus{
            return this
        }

    };


    abstract fun verify(menus: MutableList<Menu>): ProviderStatus

}