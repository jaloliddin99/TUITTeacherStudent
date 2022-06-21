package com.tuit.tuit.ui.student.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tuit.tuit.R
import com.tuit.tuit.databinding.FragmentHomeBinding
import com.tuit.tuit.databinding.FragmentOpenFileBinding


class OpenFileFragment : Fragment() {
    private var _binding: FragmentOpenFileBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentOpenFileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}