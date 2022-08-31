package com.tongue.dandelion.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.tongue.dandelion.R

class NavigationHeaderView(context: Context, attributeSet: AttributeSet?)
    : ConstraintLayout(context,attributeSet) {

    private val titleTextView: TextView
    private val backButton: FloatingActionButton
    private val secondaryButton: FloatingActionButton

    init {
        inflate(context, R.layout.custom_default_header_view, this)
        titleTextView = findViewById(R.id.defaultHeaderView_textView_title)
        backButton = findViewById(R.id.defaultHeaderView_actionButton_back)
        secondaryButton = findViewById(R.id.defaultHeaderView_actionButton_more)
    }

    fun setTitle(title: String){
        titleTextView.text = title
    }

    fun setSecondaryButtonImageResource(image:Int){
        secondaryButton.setImageResource(image)
        secondaryButton.invalidate()
    }

    fun setOnSecondaryButtonClickListener(listener: OnClickListener){
        secondaryButton.setOnClickListener { listener.onClick(it) }
    }

    fun setOnNavigationBackClickListener(listener: OnClickListener){
        backButton.setOnClickListener { listener.onClick(it) }
    }

    fun hideSecondaryButton(){
        secondaryButton.visibility = View.GONE
    }

    fun hideTitleView(){
        titleTextView.visibility = View.GONE
    }

}