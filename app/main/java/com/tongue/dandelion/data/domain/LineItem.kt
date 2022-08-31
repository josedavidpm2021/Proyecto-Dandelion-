package com.tongue.dandelion.data.domain

data class LineItem(
    var product: Product,
    var quantity: Int=1,
    )