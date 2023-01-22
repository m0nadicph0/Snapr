package com.example.snapr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_camera).setOnClickListener { onLaunchCamera() }
    }

    private fun onLaunchCamera() {
        Toast
            .makeText(this, "Launching Camera", Toast.LENGTH_LONG)
            .show()
    }
}