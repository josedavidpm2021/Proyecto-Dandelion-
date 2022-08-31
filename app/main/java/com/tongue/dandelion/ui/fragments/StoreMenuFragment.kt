package com.tongue.dandelion.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tongue.dandelion.R
import com.tongue.dandelion.data.domain.Product
import com.tongue.dandelion.data.domain.StoreDescription
import com.tongue.dandelion.data.domain.StoreVariant
import com.tongue.dandelion.databinding.FragmentStoreMenuBinding
import com.tongue.dandelion.helper.DataGenerator
import com.tongue.dandelion.helper.TabLayoutCoordinator
import com.tongue.dandelion.helper.ViewVisibilitySwitch
import com.tongue.dandelion.ui.adapters.StoreMenuAdapter
import com.tongue.dandelion.ui.viewmodels.ActivityViewModel
import com.tongue.dandelion.ui.viewmodels.LineItemViewModel
import com.tongue.dandelion.ui.views.ProductCollectionView

/** This fragment shows the product list (catalogue) of a restaurant or store **/

class StoreMenuFragment: Fragment() {

    private val activityViewModel: ActivityViewModel by activityViewModels()
    private val lineItemViewModel: LineItemViewModel by activityViewModels()
    private lateinit var navController: NavController
    private lateinit var binding: FragmentStoreMenuBinding
    private var tabLayoutCoordinator = TabLayoutCoordinator()
    private var viewVisibilitySwitch = ViewVisibilitySwitch()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStoreMenuBinding.inflate(inflater,container,false)
        navController = findNavController()

        //var items = DataGenerator.getCollectionArrayList()

        val dandelionStore = activityViewModel.currentDandelionStore
        var storeDescription = StoreDescription("","","","")
            dandelionStore?.storeVariant?.let {

                storeDescription = StoreDescription(
                    it.name,
                    dandelionStore.storeVariant.location.address,
                    dandelionStore.shippingSummary.arrivalTime,
                    dandelionStore.shippingSummary.shippingFee.fee.toPlainString()
                )
            }
        val menu = activityViewModel.currentStoreMenu


        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = StoreMenuAdapter(menu.sections,storeDescription,
            object: ProductCollectionView.NestedClickListener{
            override fun onProductViewClicked(v: View, p: Product) {
                Log.d("StoreMenuFragment","Product name clicked -> ${p.title}")
                lineItemViewModel.currentProduct = p
                navController.navigate(R.id.lineItemFragment)
            }

        })

        for (section in menu.sections){
            binding.layoutTabHeader.tabLayout.addTab(
                binding.layoutTabHeader.tabLayout.newTab().setText(section.category.title)
            )
        }

        var excludedPositions: List<Int> = listOf(0)
        tabLayoutCoordinator.attach(binding.layoutTabHeader.tabLayout,binding.recyclerView,excludedPositions)
        viewVisibilitySwitch.setUpWithRecyclerView(binding.recyclerView,binding.layoutTabHeader.root)

        if (dandelionStore != null) {
            initViewContent(dandelionStore.storeVariant)
        }

        setUpDefaultValuesOnViews()
        setUpClickListeners()
        setUpAppBarDimensions()
        return binding.root

    }

    private fun initViewContent(storeVariant: StoreVariant){
        binding.layoutTabHeader.header.setTitle(storeVariant.name)
    }

    /** The size of the app bar must be equal to the size of the tab header **/
    private fun setUpAppBarDimensions(){
        binding.layoutTabHeader.root.doOnLayout {
            binding.appBarLayout.layoutParams = CoordinatorLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                it.height
            )
        }
    }

    private fun setUpDefaultValuesOnViews(){
        binding.layoutRestaurantHeader.navigationHeader.hideTitleView()
    }

    private fun setUpClickListeners(){
        setUpNavigationClickListeners()
        setUpProductViewClickListener()
    }

    private fun setUpProductViewClickListener(){

    }

    private fun setUpNavigationClickListeners(){
        binding.layoutTabHeader.header.setOnNavigationBackClickListener {
            navController.popBackStack()
        }
        binding.layoutRestaurantHeader.navigationHeader.setOnNavigationBackClickListener{
            navController.popBackStack()
        }
        binding.layoutRestaurantHeader.navigationHeader.setOnSecondaryButtonClickListener{
            navController.navigate(R.id.lineItemFragment)
        }
        binding.layoutTabHeader.header.setOnSecondaryButtonClickListener{
        }
    }
}