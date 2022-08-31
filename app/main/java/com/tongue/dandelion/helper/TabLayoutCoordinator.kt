package com.tongue.dandelion.helper

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout

/** Helper class that synchronizes the motion of a RecyclerView with a TabLayout **/

class TabLayoutCoordinator {

    companion object{
        private const val TAG = "TabLayoutCoordinator"
    }

    private var ignoreScroll = false
    private var ignoreSelected = false

    fun attach(tabLayout: TabLayout, _recyclerView: RecyclerView, excludedPositions: List<Int>) {
        Log.d(TAG,"Setting up TabLayout with RecyclerView")
        var manager = _recyclerView.layoutManager as LinearLayoutManager
        var scrollListener: RecyclerView.OnScrollListener =
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (ignoreScroll) {
                        ignoreScroll = false
                        return
                    }
                    var currentPos = manager.findFirstVisibleItemPosition()
                    if (currentPos in excludedPositions)
                        return
                    if (currentPos >= 0) {
                        var currentTab = tabLayout.getTabAt(currentPos-1)
                        currentTab?.let {
                            ignoreSelected = true
                            it.select()
                            ignoreSelected = false
                        }
                    }
                }
            }
        var tabSelectedListener = object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (ignoreSelected)
                    return
                var currentPos = tab?.position
                if (currentPos != null) {
                    manager.scrollToPosition(currentPos+1)
                    ignoreScroll = true
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}

        }
        _recyclerView.addOnScrollListener(scrollListener)
        tabLayout.addOnTabSelectedListener(tabSelectedListener)
    }

}