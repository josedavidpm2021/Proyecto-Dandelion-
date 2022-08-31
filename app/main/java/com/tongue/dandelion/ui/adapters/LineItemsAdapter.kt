package com.tongue.dandelion.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tongue.dandelion.data.domain.LineItem
import com.tongue.dandelion.data.domain.StoreVariant
import com.tongue.dandelion.ui.views.LineItemView
import com.tongue.dandelion.ui.views.StoreCardView

class LineItemsAdapter(
    private var items: List<LineItem>,
    private var clickListener: LineItemViewClickListener
): RecyclerView.Adapter<LineItemsAdapter.LineItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LineItemsAdapter.LineItemViewHolder {
        var layoutParams = ViewGroup.MarginLayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        var view = LineItemView(parent.context,null)
        view.layoutParams = layoutParams
        return LineItemViewHolder(view,viewType,items[viewType])
    }

    override fun onBindViewHolder(holder: LineItemViewHolder, position: Int) {
        val lineItem = items[position]
        holder.getLineItemView().setValues(
            lineItem.product.title,
            lineItem.product.price.toPlainString(),
            lineItem.quantity,
            lineItem.product.imageUrl
        )
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class LineItemViewHolder(v: View, viewType: Int, lineItem: LineItem): RecyclerView.ViewHolder(v){

        private var lineItemView: LineItemView

        init {
            lineItemView = v as LineItemView
            lineItemView.chainClickListener {
                clickListener.onClick(lineItem, it)
            }
        }

        fun getLineItemView(): LineItemView {
            return lineItemView
        }

    }

    interface LineItemViewClickListener{
        fun onClick(lineItem: LineItem, view: View)
    }

}