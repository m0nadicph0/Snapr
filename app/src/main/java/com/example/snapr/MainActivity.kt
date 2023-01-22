package com.example.snapr

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener


class MainActivity : AppCompatActivity(), PermissionListener {
    companion object {
        private const val CAMERA = 2
    }

    private var resultLauncher:ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_camera).setOnClickListener { onLaunchCamera() }

        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            handleActivityResult(it)
        }
    }

    private fun onLaunchCamera() {
        Dexter.withContext(this)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(this).check();
        launchCameraActivity()
    }

    private fun launchCameraActivity() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
           resultLauncher!!.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        } else {
            Toast.makeText(this, "You have denied camera permission. You can enable it from settings", Toast.LENGTH_LONG).show()
        }
    }

    private fun handleActivityResult(result: ActivityResult?) {
        if (result!!.resultCode == Activity.RESULT_OK) {
            var thumbnail = result.data!!.extras!!.get("data") as Bitmap

            findViewById<ImageView>(R.id.iv_camera_image).setImageBitmap(thumbnail)

        }
    }

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        launchCameraActivity()
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        Toast.makeText(this, "You have denied camera permission. You can enable it from settings", Toast.LENGTH_LONG).show()
    }

    override fun onPermissionRationaleShouldBeShown(permissionRequest: PermissionRequest?, token: PermissionToken?) {
        AlertDialog.Builder(this)
            .setTitle("Permissions")
            .setMessage("Seems you have denied permissions required by the app. You can enable the permissions from settings.")
            .show()
    }


}