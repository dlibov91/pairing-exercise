package io.billie.organisations.service

import io.billie.organisations.data.OrderRepository
import io.billie.organisations.viewmodel.*
import org.springframework.stereotype.Service
import java.util.*

@Service
class OrderService(val db: OrderRepository) {

    fun createOrder(order: OrderRequest): UUID = db.create(order, OrderState.INITIATED)

    fun markOrderAsShipped(orderId: UUID) = db.updateOrderState(orderId, OrderState.SHIPPED)

    fun findOrders(orderId: UUID): List<OrderResponse> = db.findOrders(orderId)

}