package com.tongue.dandelion.ui.views

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.tongue.dandelion.R

class StoreDescriptionView(context: Context, attributeSet: AttributeSet?)
    : ConstraintLayout(context,attributeSet) {

    private val titleTextView: TextView
    private val addressTextView: TextView
    private val timeTextView: TextView
    private val priceTextView: TextView

        init {
            inflate(context, R.layout.custom_store_description_view, this)
            titleTextView = findViewById(R.id.storeDescriptionView_title)
            addressTextView = findViewById(R.id.storeDescriptionView_address)
            timeTextView = findViewById(R.id.storeDescriptionView_time)
            priceTextView = findViewById(R.id.storeDescriptionView_price)
        }

    fun setUpValues(title: String, address: String, time: String, price: String){
        titleTextView.text = title
        addressTextView.text = "Dirección: $address"
        timeTextView.text = "Tiempo de entrega: $time"
        priceTextView.text = "Costo de envío: $price"
    }

}