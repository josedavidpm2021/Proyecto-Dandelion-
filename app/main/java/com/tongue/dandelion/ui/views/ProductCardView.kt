package com.tongue.dandelion.ui.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.TransitionDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.tongue.dandelion.R

/** Custom ViewGroup that wraps related views in the context of a store product**/

class ProductCardView(context: Context, attributeSet: AttributeSet?, defaultImage: Int?)
    : ConstraintLayout(context,attributeSet){

    private val productNameTextView: TextView
    private val productDescriptionTextView: TextView
    private val productPriceTextView: TextView
    private val actionReflectorLayout: LinearLayoutCompat
    private val productPhotoImageView: ImageView
    private var chainedClickListener: View.OnClickListener? = null

    init {

        inflate(context, R.layout.custom_product_card_view, this)

        productNameTextView = findViewById(R.id.productCardView_textView_name)
        productDescriptionTextView = findViewById(R.id.productCardView_textView_description)
        productPriceTextView = findViewById(R.id.productCardView_textView_price)
        actionReflectorLayout = findViewById(R.id.productCardView_linearLayout_actionReflector)
        productPhotoImageView = findViewById(R.id.productCardView_imageView_photo)
        defaultImage?.let { productPhotoImageView.setImageResource(it) }

        setUpClickListener()

    }

    fun setValues(name: String, description: String?, price: String, imageUrl: String){

        productNameTextView.text = name
        productPriceTextView.text = "${price}\$"
        description?.let { productDescriptionTextView.text = it }

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
            var colors = arrayOf(
                ColorDrawable(resources.getColor(R.color.md_theme_light_onPrimary,null)),
                ColorDrawable(resources.getColor(R.color.md_theme_light_primary,null)),
            )
            var transition = TransitionDrawable(colors)
            actionReflectorLayout.background = transition
            transition.startTransition(500)
            transition.reverseTransition(500)
            chainedClickListener?.onClick(it)
        }
    }
}