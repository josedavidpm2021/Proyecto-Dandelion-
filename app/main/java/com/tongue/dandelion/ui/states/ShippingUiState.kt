package com.tongue.dandelion.ui.states

import com.tongue.dandelion.data.domain.Fulfillment

sealed class ShippingUiState {
    data class FulfillmentCreated(val fulfillment: Fulfillment): ShippingUiState()
    data class ErrorWhenCreatingFulfillment(val message: String): ShippingUiState()
}