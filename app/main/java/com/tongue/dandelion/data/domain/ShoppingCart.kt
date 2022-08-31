package com.tongue.dandelion.data.domain

data class ShoppingCart(
    var items: ArrayList<LineItem> = ArrayList(),
    var instructions: String? = null
)