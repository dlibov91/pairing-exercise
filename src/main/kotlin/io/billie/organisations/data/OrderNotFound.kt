package io.billie.organisations.data

class OrderNotFound(val orderId: String) : RuntimeException("Unable to find order by $orderId")