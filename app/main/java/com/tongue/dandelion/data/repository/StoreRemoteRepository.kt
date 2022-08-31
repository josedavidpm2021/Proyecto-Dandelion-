package com.tongue.dandelion.data.repository

import com.tongue.dandelion.data.domain.*
import com.tongue.dandelion.data.domain.Collection
import com.tongue.dandelion.data.domain.dto.DandelionStore
import com.tongue.dandelion.data.network.RetrofitInstance
import com.tongue.dandelion.helper.AppLog
import com.tongue.dandelion.ui.viewmodels.DandelionViewModel
import javax.inject.Singleton

@Singleton
class StoreRemoteRepository {

    companion object{
        const val TAG = "StoreRemoteRepository"
    }

    suspend fun getNearestStores(position: Position): List<DandelionStore> {
        AppLog.d(TAG,"Retrieving nearest stores...")
        val response = RetrofitInstance.shoppingApi.getAllAvailableStores(position)
        if (!response.ok){
            AppLog.d(TAG,"Unsatisfied request")
            throw Exception("Unsatisfied request")
        }

        AppLog.d(TAG,"ok")
        return response.success.payload
    }

    private suspend fun getStoreCollections(storeVariantId: String): List<Collection>{
        AppLog.d(TAG,"Retrieving Store Collections...")
        val response = RetrofitInstance.shoppingApi.getStoreCollections(storeVariantId)
        if (!response.ok){
            AppLog.d(TAG,"Unsatisfied request")
            throw Exception("Unsatisfied request")
        }
        AppLog.d(TAG,"ok")
        return response.success.payload
    }

    private suspend fun getStoreProductsByCollection(collectionId: String): List<Product>{
        AppLog.d(TAG,"Retrieving Collection Products...")
        val response = RetrofitInstance.shoppingApi.getCollectionProducts(collectionId)
        if (!response.ok){
            AppLog.d(TAG,"Unsatisfied request")
            throw Exception("Unsatisfied request")
        }
        AppLog.d(TAG,"ok")
        return response.success.payload
    }

    suspend fun getStoreMenu(storeVariantId: String): Menu{

        AppLog.d(TAG,"Retrieving Store Menu Sections")

        val categories = getStoreCollections(storeVariantId)

        val menuSections = ArrayList<MenuSection>()

        for (category in categories){
            val products = getStoreProductsByCollection(category.id.toString())
            val menuSection = MenuSection(category,products)
            menuSections.add(menuSection)
        }

        AppLog.d(TAG,"ok")

        return Menu(menuSections)

    }

}