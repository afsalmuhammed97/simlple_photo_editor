package com.practies.photoeditor.Ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.practies.photoeditor.R

import com.practies.photoeditor.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

     private var _binding: FragmentHomeBinding?=null
       private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding= FragmentHomeBinding.inflate(inflater,container,false)

        binding.openCameraBt.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_cameraFragment)
        }

        binding.openGallery.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_editorFragment)
        }

        return  binding.root
    }


}