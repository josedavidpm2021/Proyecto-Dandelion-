package com.tongue.dandelion.data.domain.dto

import com.tongue.dandelion.data.domain.ShippingSummary
import com.tongue.dandelion.data.domain.StoreVariant

data class DandelionStore(
    val storeVariant: StoreVariant,
    val shippingSummary: ShippingSummary
) {
}