package com.practies.photoeditor.Utill

import android.Manifest

object Constants {
    const val TAG="cameraX"
    const val  FILE_NAME_FORMAT = "yy-MM-dd--HH-ss--SSS"
    const val  REQUEST_CODE_PERMISSIONS=123
    val REQUIRED_PERMISSIONS= arrayOf(Manifest.permission.CAMERA)

}