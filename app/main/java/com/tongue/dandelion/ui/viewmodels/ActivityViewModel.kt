package com.tongue.dandelion.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.tongue.dandelion.data.domain.*
import com.tongue.dandelion.data.domain.dto.DandelionStore
import com.tongue.dandelion.helper.AppLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class ActivityViewModel: ViewModel() {

    lateinit var authentication: Authentication
    var position: Position = Position(0f,0f,"")
    var dandelionStores: List<DandelionStore>? = null
    lateinit var currentDandelionStore: DandelionStore
    lateinit var currentStoreMenu: Menu
    var checkout: Checkout = Checkout()
    lateinit var fulfillment: Fulfillment
    private var fetchJob : Job? = null

}