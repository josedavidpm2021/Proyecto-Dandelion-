package com.tongue.dandelion.ui.states

import com.tongue.dandelion.data.domain.Authentication
import com.tongue.dandelion.data.domain.dto.DandelionStore

sealed class DandelionUiState {
    data class AuthenticationPreferencesFound(val authentication: Authentication): DandelionUiState()
    object AuthenticationPreferencesNotFound: DandelionUiState()
    data class StoresFound(val dandelionStores: List<DandelionStore>): DandelionUiState()
    object ErrorFoundWhenRetrievingNearestStores: DandelionUiState()
}