package com.tongue.dandelion.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.tongue.dandelion.ui.states.LoginUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class LoginViewModel : ViewModel() {

    private val _uiState = MutableSharedFlow<LoginUiState>()
    var uiState : SharedFlow<LoginUiState> = _uiState.asSharedFlow()
    private var fetchJob : Job? = null

    fun onLoginButtonClicked(userName: String, password: String){

    }

}