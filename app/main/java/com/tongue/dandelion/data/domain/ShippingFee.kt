package com.tongue.dandelion.data.domain

import java.math.BigDecimal

data class ShippingFee(
    var fee: BigDecimal, // Coste de envío
    var temporalAccessToken: TemporalAccessToken? // Token recibido por el servidor para validar el coste de envío
)