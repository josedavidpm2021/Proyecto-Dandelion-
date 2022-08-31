package com.tongue.dandelion.di

import com.tongue.dandelion.data.repository.CheckoutRemoteRepository
import com.tongue.dandelion.data.repository.FulfillmentRemoteRepository
import com.tongue.dandelion.data.repository.MerchantRemoteRepository
import com.tongue.dandelion.data.repository.StoreRemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Singleton
    @Provides
    fun provideMerchantRemoteRepository(): MerchantRemoteRepository{
        return MerchantRemoteRepository()
    }

    @Singleton
    @Provides
    fun provideStoreRemoteRepository(): StoreRemoteRepository{
        return StoreRemoteRepository()
    }

    @Singleton
    @Provides
    fun provideCheckoutRemoteRepository(): CheckoutRemoteRepository{
        return CheckoutRemoteRepository()
    }

    @Singleton
    @Provides
    fun provideFulfillmentRemoteRepository(): FulfillmentRemoteRepository{
        return FulfillmentRemoteRepository()
    }

}