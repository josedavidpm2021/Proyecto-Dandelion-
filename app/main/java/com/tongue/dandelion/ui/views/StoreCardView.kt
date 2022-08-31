package com.tongue.dandelion.ui.views

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.TransitionDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.tongue.dandelion.R


class StoreCardView(context: Context, attributeSet: AttributeSet?)
    :ConstraintLayout(context,attributeSet){

    private lateinit var photoImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var priceTextView: TextView
    private lateinit var timeTextView: TextView
    private var chainedClickListener: View.OnClickListener? = null

        init {
            inflate(context, R.layout.custom_restaurant_card_view,this)

            photoImageView = findViewById(R.id.restaurantCardView_imageView_photo)
            titleTextView = findViewById(R.id.restaurantCardView_textView_title)
            priceTextView = findViewById(R.id.restaurantCardView_textView_price)
            timeTextView = findViewById(R.id.restaurantCardView_textView_time)

            setUpClickListener()
        }

    fun setValues(title: String, price: String, minutes: Int, photo: Bitmap?, imageUrl: String){
        //photo?.let { photoImageView.setImageBitmap(it) }

        Glide.
            with(context)
            .load(imageUrl)
            .into(photoImageView)

        titleTextView.text = title
        val shippingRate = String.format(
            resources.getString(R.string.stores_shipping_rate),
            price)
        priceTextView.text = shippingRate
        var expectedMinutes = minutes + 10
        timeTextView.text = "${minutes}-${expectedMinutes} min"
    }

    private fun setUpClickListener(){
        setOnClickListener {
            chainedClickListener?.onClick(it)
        }
    }

    /** To maintain the custom behavior chain a new click listener using this method**/
    fun chainClickListener(listener: OnClickListener){
        this.chainedClickListener = listener
    }

}