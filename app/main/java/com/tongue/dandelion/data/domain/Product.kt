package com.tongue.dandelion.data.domain

import android.graphics.Bitmap
import java.math.BigDecimal

data class Product(
    var id: Long,
    var description:String?,
    var storeVariant: StoreVariant,
    val title:String,
    val price: BigDecimal,
    var photo: Bitmap?,
    val imageUrl: String
    )
