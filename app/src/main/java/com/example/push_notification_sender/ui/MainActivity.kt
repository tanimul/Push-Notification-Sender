package com.example.push_notification_sender.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.push_notification_sender.databinding.ActivityMainBinding
import com.example.push_notification_sender.services.MyFirebaseMessagingService
import com.example.push_notification_sender.utils.extentions.toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.messaging.FirebaseMessaging


class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    var token = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MyFirebaseMessagingService.sharedPref =
            getSharedPreferences("sharedPref", Context.MODE_PRIVATE)


        binding.btSendMessageServer.setOnClickListener {
            toast("Sending Message from Server using Firebase Cloud Messaging")
        }
        binding.btSendMessageFcm.setOnClickListener {


            if (checkGooglePlayServices()) {
                toast("Sending Message from Firebase Cloud Messaging")
                FirebaseMessaging.getInstance().token.addOnCompleteListener {
                    if (!it.isSuccessful) {
                        Log.d(TAG, "onCreate: ${it.exception}")
                        return@addOnCompleteListener
                    }

                    token = it.result
                    Log.d(TAG, "onCreate: $token")
                    //MyFirebaseMessagingService.token = token
                    FirebaseMessaging.getInstance().subscribeToTopic("TEST_FUAD")

                }
            }

        }

    }

    private fun checkGooglePlayServices(): Boolean {
        val status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
        return if (status != ConnectionResult.SUCCESS) {
            Log.e("TAG", "Error")
            false
        } else {
            Log.i("TAG", "Google play services updated")
            true
        }
    }
}