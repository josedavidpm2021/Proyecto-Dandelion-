package com.tongue.dandelion.data.network

import com.android.volley.Response
import com.tongue.dandelion.data.domain.StoreVariant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Header

class RetrofitInstance {

    companion object{

        val BASE_URL = "http://192.168.100.10:8080/"

        private val retrofitInstance by lazy {

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        }

        val shoppingApi: ApiInterface by lazy(){
            retrofitInstance.create(ApiInterface::class.java)
        }

    }

}