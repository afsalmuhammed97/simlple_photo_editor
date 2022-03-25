package com.practies.photoeditor.Ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.ImageCapture
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.practies.photoeditor.R
import com.practies.photoeditor.Utill.Constants
import com.practies.photoeditor.databinding.FragmentCameraBinding
import java.io.File
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.practies.photoeditor.MainActivity
import com.practies.photoeditor.Utill.Constants.FILE_NAME_FORMAT
import com.practies.photoeditor.Utill.Constants.MSG
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class CameraFragment : Fragment() {

    private  var _binding: FragmentCameraBinding?=null
    private val binding get() = _binding!!

    private var imageCapture:ImageCapture?=null
    private lateinit var outPutDirectory:File


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View{


           _binding= FragmentCameraBinding.inflate(inflater,container,false)

                 outPutDirectory=getOutPutDirectory()

    return    binding.root
    }



    private fun getOutPutDirectory(): File {
             val mediaDir=activity?.externalMediaDirs?.firstOrNull()?.let { mFile ->
                 File(mFile, resources.getString(R.string.app_name)).apply {
                     mkdirs()
                 }
             }
        return  if (mediaDir != null && mediaDir.exists()) mediaDir else activity?.filesDir!!
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (permissionGranted()){
            startCamera()

        }else{
            ActivityCompat.requestPermissions(
                requireActivity(),Constants.REQUIRED_PERMISSIONS,
                Constants.REQUEST_CODE_PERMISSIONS
            )
        }

    }

    private fun startCamera() {
              val cameraProviderFuture= context?.let { ProcessCameraProvider.getInstance(it) }
        cameraProviderFuture?.addListener({

            val cameraProvider : ProcessCameraProvider=cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {  mPreview ->
                    mPreview.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }
            imageCapture= ImageCapture.Builder()
                .build()
            val cameraSelector= CameraSelector.DEFAULT_FRONT_CAMERA



            try {
                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(this,cameraSelector,preview,imageCapture)
            }catch (e:Exception){
                Log.d(Constants.TAG,"Start camera fail :",e )

            }


        }, context?.let { ContextCompat.getMainExecutor(it) })






         binding.takePhoto.setOnClickListener{
             takePhoto()
         }






    }

    @SuppressLint("SimpleDateFormat")
    private fun takePhoto() {
        val imageCapture=imageCapture?:return
        val photoFile=File(
            outPutDirectory,SimpleDateFormat(FILE_NAME_FORMAT,
            Locale.getDefault()).format(System.currentTimeMillis())+".jpg")

        val outPutOption=ImageCapture
            .OutputFileOptions
            .Builder(photoFile)
            .build()


         imageCapture.takePicture(
             outPutOption,ContextCompat.getMainExecutor(requireContext()),

             object :ImageCapture.OnImageSavedCallback{
                 override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {

                     val savedUri=Uri.fromFile(photoFile)

                       Toast.makeText(context,"$MSG  $savedUri" ,Toast.LENGTH_SHORT).show()
                 }

                 override fun onError(exception: ImageCaptureException) {
                     Log.e(Constants.TAG,"error : ${exception.message}",exception)
                 }

             }
         )


    }


    private fun permissionGranted()=

        Constants.REQUIRED_PERMISSIONS.all {
            context?.let { it1 -> ContextCompat.checkSelfPermission(it1.applicationContext,it) } ==PackageManager.PERMISSION_GRANTED
        }






}