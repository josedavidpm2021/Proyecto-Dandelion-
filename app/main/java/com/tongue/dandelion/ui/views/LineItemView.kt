package com.tongue.dandelion.ui.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.TransitionDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.tongue.dandelion.R

class LineItemView(context: Context, attributeSet: AttributeSet?)
    : ConstraintLayout(context,attributeSet) {

    private val productNameTextView: TextView
    private val lineItemSubtotalTextView: TextView
    private val productPhotoImageView: ImageView
    private val layoutQuantity: ConstraintLayout
    private var chainedClickListener: View.OnClickListener? = null

        init {


            inflate(context, R.layout.custom_line_item_view, this)

            productNameTextView = findViewById(R.id.lineItemView_textView_name)
            lineItemSubtotalTextView = findViewById(R.id.lineItemView_textView_price)
            layoutQuantity = findViewById(R.id.lineItemView_layout_quantity)
            productPhotoImageView = findViewById(R.id.lineItemView_imageView_photo)

            setUpClickListener()

        }

    fun setUpQuantityButtonsClickListeners(
        increaseClickListener: View.OnClickListener,
        decreaseClickListener: View.OnClickListener){

        val increaseButton = layoutQuantity.getViewById(R.id.button_increase) as Button
        val decreaseButton = layoutQuantity.getViewById(R.id.button_decrease) as Button
        increaseButton.setOnClickListener {
            increaseClickListener
        }
        decreaseButton.setOnClickListener {
            decreaseClickListener
        }
    }

    fun setValues(name: String, price: String, quantity: Int, imageUrl: String){
        productNameTextView.text = name
        val quantityTextView = layoutQuantity.getViewById(R.id.textView_quantity) as TextView
        quantityTextView.text = quantity.toString()
        val subtotal = String.format(resources.getString(R.string.custom_line_item_view_subtotal), price.toBigDecimal().multiply(quantity.toBigDecimal()))
        lineItemSubtotalTextView.text = subtotal

        Glide.
        with(context)
            .load(imageUrl)
            .into(productPhotoImageView)

    }


    /** To maintain the custom behavior chain a new click listener using this method**/
    fun chainClickListener(listener: OnClickListener){
        this.chainedClickListener = listener
    }

    /** This implementation of ClickListener adds an extra animation **/
    private fun setUpClickListener(){
        setOnClickListener {
            chainedClickListener?.onClick(it)
        }
    }

}