package com.tongue.dandelion.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tongue.dandelion.data.domain.Collection
import com.tongue.dandelion.data.domain.MenuSection
import com.tongue.dandelion.data.domain.StoreDescription
import com.tongue.dandelion.ui.views.ProductCollectionView
import com.tongue.dandelion.ui.views.StoreDescriptionView

/** This Adapter admits creation of different view holder wrapped classes **/

class StoreMenuAdapter(
    private var items: List<MenuSection>,
    private var storeDescription: StoreDescription,
    private var nestedOnClickListener: ProductCollectionView.NestedClickListener
): RecyclerView.Adapter<StoreMenuAdapter.StoreMenuViewHolder>() {

    companion object{
        private const val TAG = "StoreMenuAdapter"
        private const val COLLECTION_TYPE = 1
        private const val DESCRIPTION_TYPE = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreMenuViewHolder {

        var layoutParams = ViewGroup.MarginLayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        return if (viewType== DESCRIPTION_TYPE){
            var view = StoreDescriptionView(parent.context,null)
            layoutParams.topMargin = 40
            layoutParams.bottomMargin = 20
            view.layoutParams = layoutParams
            StoreMenuViewHolder(view,viewType)
        }else{
            var view = ProductCollectionView(parent.context,null)
            layoutParams.topMargin = 20
            view.layoutParams = layoutParams
            StoreMenuViewHolder(view,viewType)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position==0)
            return DESCRIPTION_TYPE
        return COLLECTION_TYPE
    }

    override fun onBindViewHolder(holder: StoreMenuViewHolder, position: Int) {
        var viewType = getItemViewType(position)
        if (viewType== COLLECTION_TYPE){
            holder.getCollectionView()?.inflateLayoutFromProductList(
                items[position-1].products,
                nestedOnClickListener
            )
            holder.getCollectionView()?.setTitle(items[position-1].category.title)
        }else
            holder.getStoreDescriptionView()?.setUpValues(
                storeDescription.title,
                storeDescription.address,
                storeDescription.time,
                storeDescription.price
            )
    }

    override fun getItemCount(): Int {
        var size = items.size + 1
        return size
    }

    inner class StoreMenuViewHolder(v: View, viewType: Int): RecyclerView.ViewHolder(v){
        private var descriptionView: StoreDescriptionView?=null
        private var collectionView: ProductCollectionView?=null
        init {
            if (viewType== DESCRIPTION_TYPE){
                descriptionView = v as StoreDescriptionView
            }else{
                collectionView = v as ProductCollectionView
            }

        }
        fun getCollectionView(): ProductCollectionView?{
            return collectionView
        }
        fun getStoreDescriptionView(): StoreDescriptionView?{
            return descriptionView
        }
    }

}