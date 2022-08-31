package com.tongue.dandelion.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.LatLng
import com.tongue.dandelion.R
import com.tongue.dandelion.data.domain.*
import com.tongue.dandelion.databinding.FragmentCheckoutBinding
import com.tongue.dandelion.helper.DataGenerator
import com.tongue.dandelion.ui.states.CheckoutUiState
import com.tongue.dandelion.ui.states.StoreUiState
import com.tongue.dandelion.ui.viewmodels.ActivityViewModel
import com.tongue.dandelion.ui.viewmodels.CheckoutViewModel
import com.tongue.dandelion.ui.viewmodels.StoresViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/** This fragment represents the final part of a checkout process
 *  where the application shows the user the final amount to pay,
 *  shipping details and asks him to finish the shopping process
 *  and start the shipping process **/

@AndroidEntryPoint
class CheckoutFragment: Fragment() {

    private val activityViewModel: ActivityViewModel by activityViewModels()
    private val viewModel: CheckoutViewModel by viewModels()
    private lateinit var binding: FragmentCheckoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCheckoutBinding.inflate(inflater,container,false)

        val origin = LatLng(
            activityViewModel.position.latitude.toDouble(),
            activityViewModel.position.longitude.toDouble()
        )
        val destination = LatLng(-0.2086989341940772, -78.4889380995957)


        val mapsFragment = GoogleMapsFragment(origin,object : GoogleMapsFragment.GoogleMapReadyCallBack{
            override fun onMapReady(mapsFragment: GoogleMapsFragment) {
                mapsFragment.drawPolylineBetweenTwoPoints(origin,destination)
            }
        })

        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.add(R.id.maps_container,mapsFragment)
            ?.commit()

        launchOnCreate()

        setUpClickListeners()
        initViewContent()

        return binding.root

    }

    private fun launchOnCreate(){
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.uiState.collect {
                    when(it){
                        is CheckoutUiState.CheckoutCreatedSuccessfully -> {
                            activityViewModel.checkout.id = it.checkout.id
                            findNavController().navigate(R.id.shippingFragment2)
                        }
                        is CheckoutUiState.ErrorOnCreateCheckout -> {
                            Toast.makeText(requireContext(),it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun initViewContent(){
        binding.navigationHeaderViewHeader.setTitle(activityViewModel.currentDandelionStore.storeVariant.name)
    }

    private fun setUpClickListeners(){
        binding.buttonFinish.setOnClickListener {
            val filledCheckout = fillCheckout()
            viewModel.completeCheckout(filledCheckout,activityViewModel.authentication.jwt)
        }
    }

    private fun fillCheckout():Checkout{
        var checkout = activityViewModel.checkout
        checkout.storeVariant = activityViewModel.currentDandelionStore.storeVariant
        checkout.shoppingCart = activityViewModel.checkout.shoppingCart
        val shippingInfo = ShippingInfo(
            activityViewModel.position,
            activityViewModel.currentDandelionStore.storeVariant.location,
            "shipping-session-token",
            activityViewModel.currentDandelionStore.shippingSummary.shippingFee.fee
        )
        checkout.shippingInfo = shippingInfo
        var paymentMethod = PaymentMethod.CREDIT_CARD
        if (binding.layoutPaymentMethod.radioButtonCash.isChecked)
            paymentMethod = PaymentMethod.CASH
        val paymentInfo = PaymentInfo(paymentMethod,"payment-session-token")
        checkout.paymentInfo = paymentInfo
        return checkout
    }

}