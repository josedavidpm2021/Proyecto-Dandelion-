package com.tongue.dandelion.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tongue.dandelion.data.domain.Checkout
import com.tongue.dandelion.data.repository.CheckoutRemoteRepository
import com.tongue.dandelion.data.repository.FulfillmentRemoteRepository
import com.tongue.dandelion.helper.AppLog
import com.tongue.dandelion.ui.states.CheckoutUiState
import com.tongue.dandelion.ui.states.ShippingUiState
import com.tongue.dandelion.ui.states.ShoppingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShippingViewModel @Inject constructor(
    private val fulfillmentRemoteRepository: FulfillmentRemoteRepository
): ViewModel() {

    companion object{
        const val TAG = "ShippingViewModel"
    }

    private val _uiState = MutableSharedFlow<ShippingUiState>()
    var uiState : SharedFlow<ShippingUiState> = _uiState.asSharedFlow()
    private var fetchJob : Job? = null

    fun beginFulfillment(checkout: Checkout, jwt: String){
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                val fulfillment = fulfillmentRemoteRepository.beginFulfillment(jwt, checkout)
                _uiState.emit(ShippingUiState.FulfillmentCreated(fulfillment))
            }catch (e: Exception){
                AppLog.d(CheckoutViewModel.TAG,"$e")
                _uiState.emit(ShippingUiState.ErrorWhenCreatingFulfillment("$e"))
            }
        }
    }

}