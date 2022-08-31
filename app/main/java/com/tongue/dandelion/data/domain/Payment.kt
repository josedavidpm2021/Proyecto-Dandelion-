package com.tongue.dandelion.data.domain

data class Payment(
    val paymentId: String,
    val statusCode: PaymentStatusCode
) {
}

enum class PaymentStatusCode{
    P1,P2,P3,P4,P5
}