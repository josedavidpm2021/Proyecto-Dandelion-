package com.tongue.dandelion.data.domain

import java.math.BigDecimal

data class ShippingInfo(
    var customerPosition: Position,
    var storePosition: Position,
    val shippingSession: String,
    var fee: BigDecimal // Coste de envío
)