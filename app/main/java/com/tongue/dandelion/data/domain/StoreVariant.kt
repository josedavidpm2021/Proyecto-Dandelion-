package com.tongue.dandelion.data.domain

data class StoreVariant(
    var id: Long,
    var name: String,
    var location: Position,
    var storeImageURL: String,
    var summary: ShippingSummary // información extra como 'tiempo de llegada' y 'coste de envío'
)