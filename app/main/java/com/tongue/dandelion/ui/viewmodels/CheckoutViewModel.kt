package com.tongue.dandelion.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tongue.dandelion.data.domain.Checkout
import com.tongue.dandelion.data.repository.CheckoutRemoteRepository
import com.tongue.dandelion.data.repository.StoreRemoteRepository
import com.tongue.dandelion.helper.AppLog
import com.tongue.dandelion.ui.states.CheckoutUiState
import com.tongue.dandelion.ui.states.DandelionUiState
import com.tongue.dandelion.ui.states.StoreUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val checkoutRemoteRepository: CheckoutRemoteRepository
): ViewModel() {

    companion object{
        const val TAG = "CheckoutViewModel"
    }

    private val _uiState = MutableSharedFlow<CheckoutUiState>()
    var uiState : SharedFlow<CheckoutUiState> = _uiState.asSharedFlow()
    private var fetchJob : Job? = null

    fun completeCheckout(checkout: Checkout, jwt: String){
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                val createdCheckout = checkoutRemoteRepository.createAndCompleteCheckout(checkout, jwt)
                _uiState.emit(CheckoutUiState.CheckoutCreatedSuccessfully(createdCheckout))
            }catch (e: Exception){
                AppLog.d(TAG,"$e")
                _uiState.emit(CheckoutUiState.ErrorOnCreateCheckout("$e"))
            }
        }
    }

}