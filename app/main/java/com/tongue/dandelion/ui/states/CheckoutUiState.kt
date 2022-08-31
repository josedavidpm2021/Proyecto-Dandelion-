package com.tongue.dandelion.ui.states

import com.tongue.dandelion.data.domain.Checkout

sealed class CheckoutUiState {
    data class CheckoutCreatedSuccessfully(val checkout: Checkout): CheckoutUiState()
    data class ErrorOnCreateCheckout(val message: String): CheckoutUiState()
}