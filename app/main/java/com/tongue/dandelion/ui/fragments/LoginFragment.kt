package com.tongue.dandelion.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tongue.dandelion.R
import com.tongue.dandelion.databinding.FragmentLoginBinding

/** This fragment let's the user authenticate with a Google Account **/

class LoginFragment: Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater,container,false)

        setUpClickListeners()

        return binding.root

    }

    private fun setUpClickListeners(){
        binding.buttonGoogleLogin.setOnClickListener {
            findNavController().navigate(R.id.storesFragment)
        }
    }

}