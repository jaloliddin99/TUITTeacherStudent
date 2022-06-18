package com.tuit.tuit.ui.login.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tuit.tuit.databinding.FragmentSignInBinding

class FragmentSignIn:Fragment() {
    private var _binding: FragmentSignInBinding?=null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentSignInBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            back.setOnClickListener { findNavController().popBackStack() }
            register.setOnClickListener {
                val action=FragmentSignInDirections.actionFragmentSignInToFragmentSignUp()
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}