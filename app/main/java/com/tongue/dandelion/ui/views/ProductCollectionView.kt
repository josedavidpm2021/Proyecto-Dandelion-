package com.tongue.dandelion.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import com.tongue.dandelion.R
import com.tongue.dandelion.data.domain.Product

class ProductCollectionView(context: Context, attributeSet: AttributeSet?)
    : ConstraintLayout(context,attributeSet) {

    private val containerLayout: LinearLayoutCompat
    private val titleTextView: TextView
    private var alreadyInflated = false

        init {
            inflate(context, R.layout.custom_product_collection_view, this)
            containerLayout = findViewById(R.id.productCollectionView_containerLayout)
            titleTextView = findViewById(R.id.productCollectionView_title)
        }

    fun setTitle(title: String){
        titleTextView.text = title
    }

    fun inflateLayoutFromProductList(products: List<Product>,
                                     listener: NestedClickListener){

        if (alreadyInflated)
            return

        var layoutParams = ViewGroup.MarginLayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        for (product in products){
            var productView = ProductCardView(context,null,R.drawable.pizza_photo)
            productView.layoutParams = layoutParams
            productView.setValues(
                product.title,
                product.description,
                product.price.toPlainString(),
                product.imageUrl
            )
            productView.chainClickListener {
                listener.onProductViewClicked(it, product)
            }
            containerLayout.addView(productView)
        }

        alreadyInflated = true
    }

    interface NestedClickListener{
        fun onProductViewClicked(v:View, p: Product)
    }

}