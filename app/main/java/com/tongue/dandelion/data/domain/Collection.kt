package com.tongue.dandelion.data.domain

/** Products are classified in groups such as 'Hamburgers' **/

data class Collection(
    var id: Long,
    var storeVariant: StoreVariant?,
    var title: String,
    var products: List<Product>
)
