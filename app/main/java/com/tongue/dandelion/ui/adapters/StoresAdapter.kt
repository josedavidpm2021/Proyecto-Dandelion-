package com.tongue.dandelion.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tongue.dandelion.data.domain.Product
import com.tongue.dandelion.data.domain.StoreVariant
import com.tongue.dandelion.data.domain.dto.DandelionStore
import com.tongue.dandelion.helper.CustomParser
import com.tongue.dandelion.ui.views.StoreCardView

class StoresAdapter(
    private var items: List<DandelionStore>,
    private var clickListener: StoreViewClickListener
): RecyclerView.Adapter<StoresAdapter.StoreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        var layoutParams = ViewGroup.MarginLayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        var view = StoreCardView(parent.context,null)
        layoutParams.topMargin = 40
        layoutParams.bottomMargin = 20
        view.layoutParams = layoutParams
        return StoreViewHolder(view,viewType,items[viewType])
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        var store = items[position]
        holder.getStoreView().setValues(
            store.storeVariant.name,
            store.shippingSummary.shippingFee.fee.toPlainString(),
            CustomParser.getMinutesFromArrivalTimeString(store.shippingSummary.arrivalTime),
            null,
            store.storeVariant.storeImageURL
        )
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class StoreViewHolder(v: View, viewType: Int, dandelionStore: DandelionStore): RecyclerView.ViewHolder(v){

        private var storeView: StoreCardView

        init {
            storeView = v as StoreCardView
            storeView.chainClickListener {
                clickListener.onClick(dandelionStore, it)
            }
        }

        fun getStoreView(): StoreCardView{
            return storeView
        }

    }

    interface StoreViewClickListener{
        fun onClick(dandelionStore: DandelionStore, view: View)
    }

}