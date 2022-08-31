package com.tongue.dandelion.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.tongue.dandelion.data.domain.Product
import com.tongue.dandelion.ui.states.LineItemUiState
import com.tongue.dandelion.ui.states.LoginUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class LineItemViewModel: ViewModel() {

    private val _uiState = MutableSharedFlow<LineItemUiState>()
    var uiState : SharedFlow<LineItemUiState> = _uiState.asSharedFlow()
    private var fetchJob : Job? = null
    lateinit var currentProduct: Product

}