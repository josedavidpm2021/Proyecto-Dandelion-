package com.tongue.dandelion.data.network

import com.android.volley.Response
import com.tongue.dandelion.data.domain.*
import com.tongue.dandelion.data.domain.Collection
import com.tongue.dandelion.data.domain.dto.DandelionStore
import retrofit2.http.*

interface ApiInterface {

    @POST("/stores/")
    suspend fun getAllAvailableStores(@Body position: Position): ApiResponse<List<DandelionStore>>

    @GET("/stores/{storeId}/menu")
    suspend fun getStoreCollections(@Path("storeId") storeId: String): ApiResponse<List<Collection>>

    @GET("/collections/{collectionId}/products")
    suspend fun getCollectionProducts(@Path("collectionId") collectionId: String): ApiResponse<List<Product>>

    @POST("/checkout/create")
    suspend fun createCheckout(@Header("Authorization") jwt: String, @Body checkout: Checkout): ApiResponse<Checkout>

    @POST("/checkout/v2/complete")
    suspend fun createAndCompleteCheckout(@Header("Authorization") jwt: String, @Body checkout: Checkout):
            ApiResponse<Checkout>

    @POST("/fulfillment/begin")
    suspend fun beginFulfillment(@Header("Authorization") jwt: String, @Body checkout: Checkout):
            ApiResponse<Fulfillment>

    @POST("/checkout/complete")
    suspend fun completeCheckout(@Header("Authorization") jwt: String, @Header("Set-Cookie") sessionId: String): ApiResponse<Checkout>

}