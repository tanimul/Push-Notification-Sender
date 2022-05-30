package com.example.push_notification_sender.data.network

import com.example.push_notification_sender.data.model.PushNotification
import com.example.push_notification_sender.utils.Constants.Companion.CONTENT_TYPE
import com.example.push_notification_sender.utils.Constants.Companion.SERVER_KEY
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ResApis {
    @Headers("Authorization: key=$SERVER_KEY", "Content-Type:$CONTENT_TYPE")
    @POST("fcm/send")
    suspend fun postNotification(
        @Body notification: PushNotification
    ): Response<ResponseBody>
}