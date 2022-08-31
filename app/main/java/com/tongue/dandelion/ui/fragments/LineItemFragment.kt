package com.tongue.dandelion.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.tongue.dandelion.R
import com.tongue.dandelion.data.domain.LineItem
import com.tongue.dandelion.data.domain.Product
import com.tongue.dandelion.data.domain.StoreVariant
import com.tongue.dandelion.databinding.FragmentLineItemBinding
import com.tongue.dandelion.helper.AppLog
import com.tongue.dandelion.helper.DataGenerator
import com.tongue.dandelion.ui.viewmodels.ActivityViewModel
import com.tongue.dandelion.ui.viewmodels.LineItemViewModel
import com.tongue.dandelion.ui.viewmodels.StoresViewModel

/** This fragment shows the product details and lets the user add it to the shopping cart **/
/** The items inside a Shopping Cart are denominated as LineItems **/

class LineItemFragment: Fragment() {

    companion object{
        const val TAG = "LineItemFragment"
    }

    private lateinit var navController: NavController
    private lateinit var binding: FragmentLineItemBinding
    private val viewModel: LineItemViewModel by activityViewModels()
    private val activityViewModel: ActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLineItemBinding.inflate(inflater,container,false)
        navController = findNavController()

        val storeVariant = activityViewModel.currentDandelionStore.storeVariant
        val product = viewModel.currentProduct

        initViewsContent(product, storeVariant)

        setUpClickListeners()
        setUpViewsDefaultValues()
        return binding.root

    }

    private fun initViewsContent(product: Product, storeVariant: StoreVariant ){
        binding.textViewName.text = product.title
        binding.textViewDescription.text = product.description
        val price = String.format(resources.getString(R.string.line_item_product_price_value),product.price.toPlainString())
        val buttonPrice  = String.format(getString(R.string.line_item_button_add_to_cart),product.price.toPlainString())
        val deliveryTime = 39
        val arrivalTime = String.format(resources.getString(R.string.line_item_delivery_time_value),deliveryTime, deliveryTime+10)
        binding.textViewProductPriceValue.text = price
        binding.textViewProductDeliveryTimeValue.text = arrivalTime
        binding.buttonAddToShoppingCart.text = buttonPrice
        binding.navigationHeaderView.setTitle(storeVariant.name)
        binding.navigationHeaderView.hideSecondaryButton()

        Glide.
        with(requireContext())
            .load(product.imageUrl)
            .into(binding.imageViewProductImage)



    }

    private fun setUpClickListeners(){
        binding.layoutItemQuantity.buttonDecrease.setOnClickListener {
            var quantity = binding.layoutItemQuantity.textViewQuantity.text.toString().trim().toInt()
            if (quantity<2)
                return@setOnClickListener
            quantity-=1
            binding.layoutItemQuantity.textViewQuantity.text = quantity.toString()
            val buttonPrice  =
                String.format(getString(R.string.line_item_button_add_to_cart),viewModel.currentProduct.price.multiply(quantity.toBigDecimal()).toPlainString())
            binding.buttonAddToShoppingCart.text = buttonPrice
        }
        binding.layoutItemQuantity.buttonIncrease.setOnClickListener {
            var quantity = binding.layoutItemQuantity.textViewQuantity.text.toString().trim().toInt()
            quantity+=1
            binding.layoutItemQuantity.textViewQuantity.text = quantity.toString()
            val buttonPrice  =
                String.format(getString(R.string.line_item_button_add_to_cart),viewModel.currentProduct.price.multiply(quantity.toBigDecimal()).toPlainString())
            binding.buttonAddToShoppingCart.text = buttonPrice
        }
        binding.buttonAddToShoppingCart.setOnClickListener {
            val quantity = binding.layoutItemQuantity.textViewQuantity.text.toString().toInt()
            val lineItem = LineItem(viewModel.currentProduct,quantity)
            // Verify that the current product belongs to the current storeVariant
            AppLog.d(TAG,"LineItem added to current ShoppingCart")
            addLineItemToShoppingCart(lineItem)
            findNavController().popBackStack()
        }
    }

    private fun addLineItemToShoppingCart(lineItem: LineItem){
        if (activityViewModel.checkout.shoppingCart.items.size==0){
            activityViewModel.checkout.storeVariant = activityViewModel.currentDandelionStore.storeVariant
            activityViewModel.checkout.shoppingCart.items.add(lineItem)
        }else{
            if (activityViewModel.checkout.storeVariant!!.id!=activityViewModel.currentDandelionStore.storeVariant.id){
                Toast.makeText(requireContext(),"Asegurese de que los productos en su carrito" +
                        " de compras pertenezcan a la misma tienda del producto que espera agregar",Toast.LENGTH_SHORT).show()
            }else{
                activityViewModel.checkout.shoppingCart.items.add(lineItem)
            }
        }
    }

    private fun setUpViewsDefaultValues(){
        binding.navigationHeaderView.setSecondaryButtonImageResource(R.drawable.ic_baseline_more_vert_24)
    }

}