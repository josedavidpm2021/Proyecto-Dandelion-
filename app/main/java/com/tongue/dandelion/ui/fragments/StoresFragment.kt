package com.tongue.dandelion.ui.fragments

import android.os.Bundle
import android.util.Log
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
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tongue.dandelion.R
import com.tongue.dandelion.data.domain.StoreVariant
import com.tongue.dandelion.data.domain.dto.DandelionStore
import com.tongue.dandelion.databinding.FragmentStoresBinding
import com.tongue.dandelion.helper.AppLog
import com.tongue.dandelion.helper.DataGenerator
import com.tongue.dandelion.ui.adapters.StoresAdapter
import com.tongue.dandelion.ui.states.StoreUiState
import com.tongue.dandelion.ui.viewmodels.ActivityViewModel
import com.tongue.dandelion.ui.viewmodels.DandelionViewModel
import com.tongue.dandelion.ui.viewmodels.StoresViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/** This fragment shows the available restaurants, this is the main screen of our application **/

@AndroidEntryPoint
class StoresFragment:Fragment() {

    private val activityViewModel: ActivityViewModel by activityViewModels()
    private val viewModel: StoresViewModel by viewModels()
    private lateinit var binding: FragmentStoresBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStoresBinding.inflate(inflater,container,false)
        navController = findNavController()

        launchOnCreate()

        var adapter = activityViewModel.dandelionStores?.let {
            StoresAdapter(it,object: StoresAdapter.StoreViewClickListener{
                override fun onClick(dandelionStore: DandelionStore, view: View) {
                    AppLog.d(TAG,"Store clicked with name -> ${dandelionStore.storeVariant.name}")
                    activityViewModel.currentDandelionStore = dandelionStore
                    viewModel.getStoreMenu(dandelionStore.storeVariant.id.toString())
                }

            })
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        setUpClickListeners()

        return binding.root

    }

    override fun onStart() {
        super.onStart()
        showShoppingCartButton()
        setUpDefaultValues()
    }

    private fun launchOnCreate(){
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.uiState.collect {
                    when(it){
                        is StoreUiState.StoreMenuLoaded -> {
                            activityViewModel.currentStoreMenu = it.menu
                            navController.navigate(R.id.storeMenuFragment)
                        }
                        is StoreUiState.ErrorFoundWhenLoadingStoreMenu -> {
                            Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun setUpClickListeners(){
        binding.buttonOrderDetails.setOnClickListener {
            findNavController().navigate(R.id.shoppingCartFragment)
        }
    }

    private fun showShoppingCartButton(){
        if (activityViewModel.checkout.shoppingCart.items.size>0){
            binding.buttonOrderDetails.visibility = View.VISIBLE
        }else{
            binding.buttonOrderDetails.visibility = View.GONE
        }
    }

    private fun setUpDefaultValues(){
        val greetings = String.format(
            resources.getString(R.string.stores_title),
            activityViewModel.authentication.name)
        binding.textViewGreetings.text = greetings
    }

    companion object{
        private const val TAG = "StoresFragment"
    }
}