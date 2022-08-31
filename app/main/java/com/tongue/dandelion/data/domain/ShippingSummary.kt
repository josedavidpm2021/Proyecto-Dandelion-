package com.tongue.dandelion.data.domain

import java.time.LocalTime


data class ShippingSummary(
    var distance: Distance, // Distancia aproximada
    var arrivalTime: String, // Tiempo aproximado de entrega
    var shippingFee: ShippingFee // Coste de envío
)
