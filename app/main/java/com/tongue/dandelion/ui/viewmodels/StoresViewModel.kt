package com.tongue.dandelion.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tongue.dandelion.data.repository.StoreRemoteRepository
import com.tongue.dandelion.helper.AppLog
import com.tongue.dandelion.ui.states.StoreUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoresViewModel @Inject constructor(
    private val storeRemoteRepository: StoreRemoteRepository
) : ViewModel() {

    companion object{
        const val TAG = "StoresViewModel"
    }

    private val _uiState = MutableSharedFlow<StoreUiState>()
    var uiState : SharedFlow<StoreUiState> = _uiState.asSharedFlow()
    private var fetchJob : Job? = null

    fun getStoreMenu(storeVariantId: String){
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                val menu = storeRemoteRepository.getStoreMenu(storeVariantId)
                _uiState.emit(StoreUiState.StoreMenuLoaded(menu))
            }catch (e: Exception){
                AppLog.d(TAG,"$e")
                _uiState.emit(StoreUiState.ErrorFoundWhenLoadingStoreMenu("Server Not found!"))
            }
        }
    }

}