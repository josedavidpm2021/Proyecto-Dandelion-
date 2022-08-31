package com.tongue.dandelion.ui.fragments

import android.animation.Animator
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.tongue.dandelion.R
import com.tongue.dandelion.databinding.FragmentDandelionBinding
import com.tongue.dandelion.ui.states.DandelionUiState
import com.tongue.dandelion.ui.viewmodels.ActivityViewModel
import com.tongue.dandelion.ui.viewmodels.DandelionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/** This fragment shows the application logo **/

@AndroidEntryPoint
class DandelionFragment: Fragment() {

    private lateinit var binding: FragmentDandelionBinding
    private lateinit var navController: NavController
    private val activityViewModel: ActivityViewModel by activityViewModels()
    private val viewModel: DandelionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDandelionBinding.inflate(inflater,container,false)
        navController = findNavController()

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.uiState.collect {
                    when(it){
                        is DandelionUiState.AuthenticationPreferencesFound -> {
                            activityViewModel.authentication = it.authentication
                            var currentPosition = activityViewModel.position
                            if (activityViewModel.dandelionStores==null){
                                viewModel.getNearestStores(currentPosition)
                            }else{
                                navController.navigate(R.id.storesFragment)
                            }
                        }
                        is DandelionUiState.AuthenticationPreferencesNotFound -> {
                            navController.navigate(R.id.loginFragment)
                        }
                        is DandelionUiState.StoresFound -> {
                            activityViewModel.dandelionStores = it.dandelionStores
                            navController.navigate(R.id.storesFragment)
                        }
                        is DandelionUiState.ErrorFoundWhenRetrievingNearestStores -> {
                            Toast.makeText(requireContext(),"Communication lost with Api",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        startAppLogoAnimation()
    }

    private fun startAppLogoAnimation(){
        var animation = AlphaAnimation(0.0f,1.0f)
        animation.duration = 1500
        animation.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                viewModel.getAuthenticationPreferences()
                /** Also show a progress bar **/

            }
            override fun onAnimationRepeat(p0: Animation?) {
            }
        })
        binding.imageViewLogo.animation = animation
    }

    companion object{
        private const val TAG = "DandelionFragment"
    }

}