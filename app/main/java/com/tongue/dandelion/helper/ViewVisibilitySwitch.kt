package com.tongue.dandelion.helper

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ViewVisibilitySwitch {

    companion object{
        private const val TAG="ViewVisibilitySwitch"
    }

    fun setUpWithRecyclerView(_recyclerView: RecyclerView, view: View){
        Log.d(TAG,"Setting up with recycler view")
        _recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                var manager = recyclerView.layoutManager as LinearLayoutManager
                var currentPos = manager.findFirstCompletelyVisibleItemPosition()
                if (currentPos!=0){
                    view.visibility = View.VISIBLE
                }else{
                    view.visibility = View.GONE
                }
            }
        })
    }

}