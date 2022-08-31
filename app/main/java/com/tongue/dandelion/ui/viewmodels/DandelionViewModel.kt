package com.tongue.dandelion.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tongue.dandelion.data.domain.Authentication
import com.tongue.dandelion.data.domain.Position
import com.tongue.dandelion.data.repository.StoreRemoteRepository
import com.tongue.dandelion.helper.AppLog
import com.tongue.dandelion.ui.states.DandelionUiState
import com.tongue.dandelion.ui.states.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DandelionViewModel @Inject constructor(
    private val storeRemoteRepository: StoreRemoteRepository
) : ViewModel() {

    companion object{
        const val TAG = "DandelionViewModel"
    }

    private val _uiState = MutableSharedFlow<DandelionUiState>()
    var uiState : SharedFlow<DandelionUiState> = _uiState.asSharedFlow()
    private var fetchJob : Job? = null

    fun getAuthenticationPreferences(){
        /** Search on data store or user preferences
         */
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                val authentication = Authentication(
                    "Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJwYXNzIiwic3ViIjoiYnVubnkiLCJpc3MiOiJjdXN0b21lci1tYW5hZ2VtZW50LXNlcnZpY2UiLCJhdWQiOiJzaG9wcGluZy1zZXJ2aWNlIiwiYXV0aG9yaXRpZXMiOlsiRFJJVkVSIl0sImlhdCI6MTY2MTgzMDQ0NiwiZXhwIjoxNjYxOTE2ODQ2fQ.20KwOmR5Z_w1BKrAEvNuGoPX8R7xLAvgXQHG-w9Qu3Is8YJEsvNcKKyOKPZgIszlSeGEtJzPuw7EeCtj6dQL7g",
                    "alexanderommelsw@gmail.com",
                    "Valeria")
                _uiState.emit(DandelionUiState.AuthenticationPreferencesFound(authentication))
            }catch (e: Exception){
                AppLog.d(TAG,"${e}")
                _uiState.emit(DandelionUiState.AuthenticationPreferencesNotFound)
            }
        }
    }

    fun getNearestStores(position: Position){
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                val dandelionStores = storeRemoteRepository.getNearestStores(position)
                _uiState.emit(DandelionUiState.StoresFound(dandelionStores))
            }catch (e: Exception){
                AppLog.d(TAG,"$e")
                _uiState.emit(DandelionUiState.ErrorFoundWhenRetrievingNearestStores)
            }
        }
    }

}