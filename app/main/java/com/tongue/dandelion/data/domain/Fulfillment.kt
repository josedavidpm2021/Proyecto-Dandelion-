package com.tongue.dandelion.data.domain

data class Fulfillment(
    val fulfillmentId: Long,
    val checkout: Checkout,
    val payment: Payment,
    val order: Order,
    val shipping: Shipping
) {
}