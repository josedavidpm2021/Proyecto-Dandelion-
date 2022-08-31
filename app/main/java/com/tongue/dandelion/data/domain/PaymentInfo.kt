package com.tongue.dandelion.data.domain

data class PaymentInfo(
    var paymentMethod: PaymentMethod = PaymentMethod.CASH,
    var paymentSession: String
)

enum class PaymentMethod{
    CASH, CREDIT_CARD
}