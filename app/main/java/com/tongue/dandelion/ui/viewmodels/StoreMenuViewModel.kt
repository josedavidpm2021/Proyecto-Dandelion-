package com.tongue.dandelion.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.tongue.dandelion.ui.states.StoreMenuUiState
import com.tongue.dandelion.ui.states.StoreUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class StoreMenuViewModel: ViewModel() {

    private val _uiState = MutableSharedFlow<StoreMenuUiState>()
    var uiState : SharedFlow<StoreMenuUiState> = _uiState.asSharedFlow()
    private var fetchJob : Job? = null

}