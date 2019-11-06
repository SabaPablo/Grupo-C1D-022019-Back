package com.unq.viendasya.service.imple

import com.unq.viendasya.controller.apiModels.MaxiOrder
import com.unq.viendasya.controller.apiModels.MiniOrder
import com.unq.viendasya.model.Order
import com.unq.viendasya.repository.OrderRepository
import com.unq.viendasya.service.UserService
import com.unq.viendasya.service.MenuService
import com.unq.viendasya.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service

class OrderServiceImple(@Autowired val dao: OrderRepository,
                        @Autowired val clientService: UserService,
                        @Autowired val menuService: MenuService): OrderService {

    override fun findByUserId(userId: Int): List<MaxiOrder> {
        val orders = dao.findByUserId(userId)
        val maxiMenues = orders.map { order -> this.convertoMaxiMenu( order ) }

        return maxiMenues
    }

    private fun convertoMaxiMenu(order: Order): MaxiOrder {

        return MaxiOrder(
                order.menu.name,
                order.menu.description,
                order.menu.category,
                order.menu.deliveryValue,
                order.menu.urlImage,
                order.client.name,
                order.menu.price,
                order.cant
        )
    }


    override fun createOrder(data: MiniOrder): Order? {
        val client = clientService.findById(data.idClient)
        val menu = menuService.findById(data.idMenu)
        menu?.let{
            client?.let {
                val order = Order.Builder().menu(menu).client(client).build()

                return dao.save(order)
            }
        }

        return null
    }

}