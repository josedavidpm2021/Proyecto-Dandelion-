package com.tongue.dandelion.data.repository

import com.tongue.dandelion.data.domain.Checkout
import com.tongue.dandelion.data.network.RetrofitInstance
import com.tongue.dandelion.helper.AppLog
import com.tongue.dandelion.ui.viewmodels.DandelionViewModel
import javax.inject.Singleton

@Singleton
class CheckoutRemoteRepository {

    companion object{
        const val TAG = "CheckoutRemoteRepository"
    }

    suspend fun createCheckout(checkout: Checkout, jwt: String): Checkout{
        AppLog.d(TAG,"Creating new Checkout on Shopping Service...")
        val response = RetrofitInstance.shoppingApi.createCheckout(jwt,checkout)
        if (!response.ok){
            AppLog.d(TAG,"Unsatisfied request")
            throw Exception("Unsatisfied request")
        }

        AppLog.d(TAG,"ok")
        return response.success.payload
    }

    suspend fun completeCheckout(jwt: String): Checkout{
        AppLog.d(TAG,"Completing Checkout on Shopping Service...")
        val response = RetrofitInstance.shoppingApi.completeCheckout(jwt,"11111111111111111111")
        if (!response.ok){
            AppLog.d(TAG,"Unsatisfied request")
            throw Exception("Unsatisfied request")
        }

        AppLog.d(TAG,"ok")
        return response.success.payload
    }

    suspend fun createAndCompleteCheckout(checkout: Checkout, jwt: String): Checkout{
        AppLog.d(TAG,"Creating and Completing new Checkout on Shopping Service...")
        val response = RetrofitInstance.shoppingApi.createAndCompleteCheckout(jwt, checkout)
        if (!response.ok){
            AppLog.d(TAG,"Unsatisfied request")
            throw Exception("Unsatisfied request")
        }

        AppLog.d(TAG,"ok")
        return response.success.payload
    }

}