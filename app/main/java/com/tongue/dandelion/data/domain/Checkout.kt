package com.tongue.dandelion.data.domain

/** This object will be sent to the backend once the customer decides to finish de order**/

data class Checkout(
    var id: Long? = null,
    var shoppingCart: ShoppingCart = ShoppingCart(),
    var storeVariant: StoreVariant?=null,
    var paymentInfo: PaymentInfo?=null,
    var shippingInfo: ShippingInfo?=null
)
