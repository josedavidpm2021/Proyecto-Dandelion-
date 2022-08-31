package com.tongue.dandelion.ui.states

import com.tongue.dandelion.data.domain.Menu

sealed class StoreUiState {
    data class StoreMenuLoaded(val menu: Menu): StoreUiState()
    data class ErrorFoundWhenLoadingStoreMenu(val message: String): StoreUiState()
}