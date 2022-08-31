package com.tongue.dandelion.data.repository

import com.tongue.dandelion.data.domain.Checkout
import com.tongue.dandelion.data.domain.Fulfillment
import com.tongue.dandelion.data.network.RetrofitInstance
import com.tongue.dandelion.helper.AppLog
import javax.inject.Singleton

@Singleton
class FulfillmentRemoteRepository {

    companion object{
        const val TAG = "FulfillmentRemoteRepository"
    }

    suspend fun beginFulfillment(jwt: String, checkout: Checkout): Fulfillment{
        AppLog.d(TAG,"Begin fulfillment...")
        val response = RetrofitInstance.shoppingApi.beginFulfillment(jwt, checkout)
        if (!response.ok){
            AppLog.d(TAG,"Unsatisfied request")
            throw Exception("Unsatisfied request")
        }

        AppLog.d(TAG,"ok")
        return response.success.payload
    }

}