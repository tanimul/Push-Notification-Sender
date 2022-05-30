package com.example.push_notification_sender.data.network

import com.example.push_notification_sender.utils.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {
        var retrofit: Retrofit? = null
        lateinit var apiInterface: ResApis
        fun getClient(): ResApis {
            if (retrofit == null) {
                synchronized(this) {
                    retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                    apiInterface = retrofit!!.create(ResApis::class.java)
                }


            }
            return apiInterface
        }
    }
}