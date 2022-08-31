package com.tongue.dandelion.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tongue.dandelion.R
import com.tongue.dandelion.databinding.FragmentShippingBinding
import com.tongue.dandelion.ui.states.CheckoutUiState
import com.tongue.dandelion.ui.states.ShippingUiState
import com.tongue.dandelion.ui.viewmodels.ActivityViewModel
import com.tongue.dandelion.ui.viewmodels.CheckoutViewModel
import com.tongue.dandelion.ui.viewmodels.ShippingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/** This fragment shows the state of the shipping process, with the following steps:
 * 1. Send a Shipping Request
 * 2. Wait for any driver to accept the order shipping
 * 3. Track the position of the driver
 * 4. Confirm the final state of the delivery **/

@AndroidEntryPoint
class ShippingFragment: Fragment() {

    private val activityViewModel: ActivityViewModel by activityViewModels()
    private val viewModel: ShippingViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var binding: FragmentShippingBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentShippingBinding.inflate(inflater, container, false)
        navController = findNavController()
        bottomSheetBehavior = BottomSheetBehavior.from(binding.standardBottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.saveFlags = BottomSheetBehavior.SAVE_ALL
        setUpBottomSheetPeekHeight()
        setUpBottomSheetHeader()

        loadMap()
        viewModel.beginFulfillment(activityViewModel.checkout,activityViewModel.authentication.jwt)
        launchOnCreate()

        binding.navigationHeaderViewHeader.setTitle(getString(R.string.shipping_title))

        setUpClickListeners()
        return binding.root
    }

    private fun launchOnCreate(){
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.uiState.collect {
                    when(it){
                        is ShippingUiState.FulfillmentCreated -> {
                            activityViewModel.fulfillment = it.fulfillment
                            binding.layoutSheetHeader.textViewStatus.setText(R.string.shipping_searching_drivers)
                        }
                        is ShippingUiState.ErrorWhenCreatingFulfillment -> {
                            Toast.makeText(requireContext(),it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    /** BottomSheet height must be equal to its header's height**/
    private fun setUpBottomSheetPeekHeight(){
        binding.layoutSheetHeader.root.doOnLayout {
            bottomSheetBehavior.setPeekHeight(it.height,false)
        }
    }

    private fun loadMap(){
        val origin = LatLng(
            activityViewModel.position.latitude.toDouble(),
            activityViewModel.position.longitude.toDouble()
        )


        val mapsFragment = GoogleMapsFragment(origin,object : GoogleMapsFragment.GoogleMapReadyCallBack{
            override fun onMapReady(mapsFragment: GoogleMapsFragment) {
                mapsFragment.enableMyLocation()
            }
        })

        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.add(R.id.maps_container,mapsFragment)
            ?.commit()
    }

    private fun setUpBottomSheetHeader(){
        bottomSheetBehavior.addBottomSheetCallback(object:BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState==BottomSheetBehavior.STATE_EXPANDED){
                    binding.layoutSheetHeader.imageViewToggle.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                }
                if (newState==BottomSheetBehavior.STATE_COLLAPSED){
                    binding.layoutSheetHeader.imageViewToggle.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {}

        })
    }

    /** The user must confirm that the shipping information is correct**/
    private fun showShippingRequestLayout(){

    }

    /** Once the courier accepted to ship the order, this layout shows information about him**/
    private fun showCourierPositionLayout(){

    }

    /** This layout shows the status of the shipping **/
    private fun showShippingStatusLayout(){

    }

    /** Confirm if the order was delivered or not **/
    private fun showDeliveryConfirmationLayout(){

    }

    private fun setUpClickListeners(){
//        binding.layoutShippingStatus.buttonAccept.setOnClickListener {
//            binding.layoutShippingStatus.textViewStoreName.visibility = View.GONE
//            binding.layoutShippingStatus.textViewStoreAddress.visibility = View.GONE
//            binding.layoutShippingStatus.textViewCustomerAddress.visibility = View.GONE
//        }
//        binding.layoutShippingStatus.buttonReject.setOnClickListener {
//            binding.layoutShippingStatus.textViewStoreName.visibility = View.VISIBLE
//            binding.layoutShippingStatus.textViewStoreAddress.visibility = View.VISIBLE
//            binding.layoutShippingStatus.textViewCustomerAddress.visibility = View.VISIBLE
//        }
    }
}