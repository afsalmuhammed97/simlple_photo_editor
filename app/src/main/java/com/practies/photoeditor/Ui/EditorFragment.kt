package com.practies.photoeditor.Ui

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.practies.photoeditor.R
import com.practies.photoeditor.databinding.FragmentEditorBinding



class EditorFragment : Fragment() {

    private  var _binding: FragmentEditorBinding?=null
    private val binding get() = _binding!!


    private val args:EditorFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding= FragmentEditorBinding.inflate(inflater,container,false)
        return  binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (args.galleryImage== null) {

            binding.cropImageView.setImageBitmap(args.photoImage)
        } else{
            binding.cropImageView.setImageUriAsync(args.galleryImage)}

        binding.cropBt.setOnClickListener{

                val cropedImage=binding.cropImageView.croppedImage

                     binding.cropImageView.setImageBitmap(cropedImage)


        }
    }

//    private fun openCropActivity(galleryImage: Uri?) {
//
//        if (galleryImage != null) {
//            context?.let {
//                UCrop.of( galleryImage, destinationUri)
//                    .withAspectRatio(16F, 9F)
//                    .withMaxResultSize(1920, 1080)
//                    .start(it,this)
//            }
//        }
//        }

    }


