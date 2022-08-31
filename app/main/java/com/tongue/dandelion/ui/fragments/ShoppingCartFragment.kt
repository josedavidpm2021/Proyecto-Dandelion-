package com.tongue.dandelion.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tongue.dandelion.R
import com.tongue.dandelion.data.domain.LineItem
import com.tongue.dandelion.data.domain.ShoppingCart
import com.tongue.dandelion.data.domain.StoreVariant
import com.tongue.dandelion.databinding.FragmentShoppingCartBinding
import com.tongue.dandelion.helper.DataGenerator
import com.tongue.dandelion.ui.adapters.LineItemsAdapter
import com.tongue.dandelion.ui.adapters.StoresAdapter
import com.tongue.dandelion.ui.viewmodels.ActivityViewModel
import java.math.BigDecimal

/** This fragment shows the current Shopping Cart of the user **/

class ShoppingCartFragment: Fragment() {

    private lateinit var binding: FragmentShoppingCartBinding
    private val activityViewModel: ActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentShoppingCartBinding.inflate(inflater,container,false)

        var shoppingCart = activityViewModel.checkout.shoppingCart
        val storeVariant = activityViewModel.checkout.storeVariant

        var adapter = LineItemsAdapter(shoppingCart.items,object: LineItemsAdapter.LineItemViewClickListener{
            override fun onClick(lineItem: LineItem, view: View) {
                Log.d(TAG,"LineItem clicked with name -> ${lineItem.product.title}")
            }
        })

        binding.recyclerViewShoppingItems.adapter = adapter
        binding.recyclerViewShoppingItems.layoutManager = LinearLayoutManager(requireContext())


        if (storeVariant != null) {
            initViewContent(storeVariant)
        }
        updateFinalPrice(shoppingCart)
        setUpClickListeners()

        return binding.root

    }

    private fun updateFinalPrice(shoppingCart: ShoppingCart){
        var finalAmount = BigDecimal.ZERO
        for (lineItem in shoppingCart.items){
            var individualAmount = lineItem.product.price.multiply(lineItem.quantity.toBigDecimal())
            finalAmount = finalAmount.add(individualAmount)
        }
        binding.textViewTotal.text = "Total: ${finalAmount.toPlainString()}\$"
    }

    private fun initViewContent(storeVariant: StoreVariant){
        binding.navigationHeaderViewHeader.setTitle(storeVariant.name)
    }

    private fun setUpClickListeners(){
        binding.buttonContinue.setOnClickListener {
            findNavController().navigate(R.id.checkoutFragment)
        }
        binding.buttonClear.setOnClickListener {
            activityViewModel.checkout.storeVariant = null
            activityViewModel.checkout.shoppingCart = ShoppingCart()
            findNavController().popBackStack()
        }
    }

    companion object{
        private const val TAG = "ShoppingCartFragment"
    }

}