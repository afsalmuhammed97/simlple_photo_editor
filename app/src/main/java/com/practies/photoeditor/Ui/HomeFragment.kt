package com.practies.photoeditor.Ui

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.practies.photoeditor.R
import com.practies.photoeditor.Utill.Constants
import com.practies.photoeditor.Utill.Constants.CAMERA_REQUEST_CODE
import com.practies.photoeditor.Utill.Constants.GALLERY_REQUEST_CODE
import com.practies.photoeditor.Utill.Constants.REQUEST_CODE_PERMISSIONS
import com.practies.photoeditor.Utill.Constants.REQUIRED_PERMISSIONS
import com.practies.photoeditor.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

//           private val cropActivityResultContract= object :ActivityResultContract<Any?,Uri>(){
//               override fun createIntent(context: Context, input: Any?): Intent {
//
//               }
//
//               override fun parseResult(resultCode: Int, intent: Intent?): Uri {
//                   TODO("Not yet implemented")
//               }
//
//           }

     private var _binding: FragmentHomeBinding?=null
       private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding= FragmentHomeBinding.inflate(inflater,container,false)

        binding.openCameraBt.setOnClickListener {
           // findNavController().navigate(R.id.action_homeFragment_to_cameraFragment)

                  if (permissionGranted()){
                      startCameraActivity()
                  }else{
                      ActivityCompat.requestPermissions(requireActivity(),
                          Constants.REQUIRED_PERMISSIONS,
                            REQUEST_CODE_PERMISSIONS)
                  }
        }

        binding.openGallery.setOnClickListener {
           // findNavController().navigate(R.id.action_homeFragment_to_editorFragment)
          //  showRotationalDialogForPermission()
            selectImage()
        }




        return  binding.root
    }

    private fun startCameraActivity() {
            val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }


    private fun permissionGranted()=

        REQUIRED_PERMISSIONS.all {
            context?.let { it1 -> ContextCompat.checkSelfPermission(
                it1.applicationContext,it) } == PackageManager.PERMISSION_GRANTED
        }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
      //  CAMERA_REQUEST_CODE

        if (requestCode==CAMERA_REQUEST_CODE  ){
            val image:Bitmap?=  data?.getParcelableExtra<Bitmap>("data")
           // binding.imageView2.setImageBitmap(image)

            val action=HomeFragmentDirections.actionHomeFragmentToEditorFragment3(image,null)             //.actionHomeFragmentToCameraFragment()

            findNavController().navigate(action)

        }else if ( requestCode== GALLERY_REQUEST_CODE){

            val selectedImageUri = data?.data
            Log.i("GLRY","Image uri ${selectedImageUri}")
            if (null != selectedImageUri) {
                // update the preview image in the layout
               // binding.imageView2.setImageURI(selectedImageUri)

                val action=HomeFragmentDirections.actionHomeFragmentToEditorFragment3(null,selectedImageUri)             //.actionHomeFragmentToCameraFragment()

                findNavController().navigate(action)


            }
        }

        }






    private fun selectImage(){
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(Intent.createChooser(i, "Select Picture"), GALLERY_REQUEST_CODE)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.imageView2.setImageBitmap(args.corpedImage)

    }


   private fun showRotationalDialogForPermission(){
       AlertDialog.Builder(context)
           .setMessage("It look lies you have turned off permissions"+
                   "required for this feature. It can be enable under App settings!!")
           .setPositiveButton("Go to settings"){_,_->
               try {
                   val intent=Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                   val uri= Uri.fromParts("package", context?.getPackageName()  ,null)
                      intent.data=uri
                   startActivity(intent)


               }catch (e:ActivityNotFoundException){
                   e.printStackTrace()
               }
           }

           .setNegativeButton("Cancel"){dialog,_->
               dialog.dismiss()
           }
   }


}
