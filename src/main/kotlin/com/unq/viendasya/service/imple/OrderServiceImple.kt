package com.unq.viendasya.service.imple

import com.unq.viendasya.controller.apiModels.MaxiOrder
import com.unq.viendasya.controller.apiModels.MiniOrder
import com.unq.viendasya.model.MenuStatus
import com.unq.viendasya.model.Order
import com.unq.viendasya.model.OrderStatus
import com.unq.viendasya.repository.OrderRepository
import com.unq.viendasya.service.MailService
import com.unq.viendasya.service.MenuService
import com.unq.viendasya.service.OrderService
import com.unq.viendasya.service.UserService
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service

class OrderServiceImple(@Autowired val dao: OrderRepository,
                        @Autowired val clientService: UserService,
                        @Autowired val menuService: MenuService,
                        @Autowired val mailService: MailService): OrderService {

    private val logger: Logger = LogManager.getLogger("log-Process")


    override fun findByUserId(userId: Int): List<MaxiOrder> {
        val orders = dao.findByUserId(userId)
        return orders.map { order -> convertoMaxiMenu( order ) }
    }

    override fun closeOrder(dateTime: LocalDateTime) {
        val from = DateTime(dateTime.year, dateTime.monthValue, dateTime.dayOfMonth, 0, 0, 0, DateTimeZone.UTC)
        val to = DateTime(dateTime.year, dateTime.monthValue, dateTime.dayOfMonth, 23, 59, 59, DateTimeZone.UTC)
        val orders = dao.getAllOrdersDueDate(from.toLocalDateTime().toDate().time, to.toLocalDateTime().toDate().time)
        val list = orders.groupBy{it.menu.id}
        list.forEach{
            it.value.forEach { order ->
                order.close(it.value.size)
                mailService.sendSimpleMessage(order.client.email,"Orden N"+ order.id, "tu orden fue confirmada, en dos dias te llega" )
                mailService.sendSimpleMessage(order.menu.provider.email,"Orden N"+ order.id, "EL pedido fue confirmado, mandale al morfi" )
                logger.info("order closed id: ${order.id}")
                dao.save(order)
            }
        }
    }

    override fun valuateOrder(orderId: Int, valoration: Int): MaxiOrder? {
        val optionalOrder = dao.findById(orderId)
        if(optionalOrder.isPresent){
            val order = optionalOrder.get()
            order.rate = valoration
            order.menu.rate(valoration)
            if(order.menu.status == MenuStatus.CANCELED) {
                mailService.sendSimpleMessage(order.menu.provider.email, "Tu menu se cancelo", "Tenes un menu con mas de 5 calificaciones negativas... cocina bien")
            }
            order.status = OrderStatus.Closed
            dao.save(order)
            return convertoMaxiMenu(order)
        }
        return null
    }

    private fun convertoMaxiMenu(order: Order): MaxiOrder {

        return MaxiOrder(
                order.menu.name,
                order.id,
                order.menu.description,
                order.menu.category,
                order.menu.deliveryValue,
                order.menu.urlImage,
                order.client.name,
                order.menu.price,
                order.status,
                order.cant
        )
    }


    override fun createOrder(data: MiniOrder): Order? {
        val client = clientService.findById(data.idClient)
        val menu = menuService.findById(data.idMenu)
        menu?.let{
            client?.let {
                val order = Order.Builder().menu(menu).client(client).date(org.joda.time.LocalDateTime(data.deliveryDate)).cant(data.cant).delivery(data.delivery).build()
                logger.info("order created")
                return dao.save(order)
            }
        }

        return null
    }

    

}