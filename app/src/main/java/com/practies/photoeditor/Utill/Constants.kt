package com.practies.photoeditor.Utill

import android.Manifest

object Constants {
    const val TAG="cameraX"
    const val  FILE_NAME_FORMAT = "yy-MM-dd--HH-ss--SSS"
    const val  REQUEST_CODE_PERMISSIONS=123
    val REQUIRED_PERMISSIONS= arrayOf(Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE)
   const val MSG="Photo saved"
    const val CAMERA_REQUEST_CODE=1
    const val GALLERY_REQUEST_CODE=2

    const val CAMERA_PERMISSION=android.Manifest.permission.CAMERA
    const val GALLERY_PERMISSION=android.Manifest.permission.READ_EXTERNAL_STORAGE

}