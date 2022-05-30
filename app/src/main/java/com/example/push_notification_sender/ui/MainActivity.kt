package com.example.push_notification_sender.ui

import android.content.ContentValues
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.push_notification_sender.data.model.NotificationData
import com.example.push_notification_sender.data.model.PushNotification
import com.example.push_notification_sender.data.network.ApiClient
import com.example.push_notification_sender.databinding.ActivityMainBinding
import com.example.push_notification_sender.services.MyFirebaseMessagingService
import com.example.push_notification_sender.utils.extentions.toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val TOPIC = "/topics/fuad_test"
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
            setNotification()
        }

        init()


    }

    private fun setNotification() {

        val title = binding.etNotification.text.toString()
        val message = binding.etNotificationDesc.text.toString()
        val recipientToken = token
        if (title.isNotEmpty() && message.isNotEmpty() && recipientToken.isNotEmpty()) {
            PushNotification(
                NotificationData(title, message),
                recipientToken
            ).also {
                sendNotification(it)
            }
        }
    }

    private fun sendNotification(notification: PushNotification) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiClient.getClient().postNotification(notification)
                if (response.isSuccessful) {
                    Log.d(TAG, "Response: ${Gson().toJson(response)}")
                } else {
                    Log.e(ContentValues.TAG, response.errorBody().toString())
                }
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
        }
    }

    private fun checkGooglePlayServices(): Boolean {
        val status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
        return if (status != ConnectionResult.SUCCESS) {
            Log.e(TAG, "Error")
            false
        } else {
            Log.i(TAG, "Google play services updated")
            true
        }
    }

    private fun init() {
        if (checkGooglePlayServices()) {
            toast("Sending Message from Firebase Cloud Messaging")
            FirebaseMessaging.getInstance().token.addOnCompleteListener {
                if (!it.isSuccessful) {
                    Log.d(TAG, "onCreate: ${it.exception}")
                    return@addOnCompleteListener
                }

                token = it.result
                Log.d(TAG, "onCreate: $token")
                MyFirebaseMessagingService.token = token
                FirebaseMessaging.getInstance().subscribeToTopic(TOPIC);
            }
        }


    }
}