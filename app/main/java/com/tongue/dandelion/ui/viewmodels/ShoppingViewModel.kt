package com.tongue.dandelion.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.tongue.dandelion.ui.states.ShoppingUiState
import com.tongue.dandelion.ui.states.StoreMenuUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class ShoppingViewModel: ViewModel() {

    private val _uiState = MutableSharedFlow<ShoppingUiState>()
    var uiState : SharedFlow<ShoppingUiState> = _uiState.asSharedFlow()
    private var fetchJob : Job? = null

}