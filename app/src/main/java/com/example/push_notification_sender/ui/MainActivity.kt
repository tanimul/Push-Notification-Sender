package com.example.push_notification_sender.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.push_notification_sender.databinding.ActivityMainBinding
import com.example.push_notification_sender.utils.extentions.toast


class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "onCreate: ")

        binding.btSendMessageFcm.setOnClickListener {
            toast("Sending Message from Firebase Cloud Messaging")


        }

        binding.btSendMessageServer.setOnClickListener {
            toast("Sending Message from Server using Firebase Cloud Messaging")
        }

    }
}