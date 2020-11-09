package com.fit.ba.rukaljubavi.Services

import com.fit.ba.rukaljubavi.Helper.FirebaseConstants.Companion.CONTENT_TYPE
import com.fit.ba.rukaljubavi.Helper.FirebaseConstants.Companion.SERVER_KEY
import com.fit.ba.rukaljubavi.Models.PushNotification
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface PushNotificationService {
    @Headers("Authorization: key=$SERVER_KEY", "Content-Type:$CONTENT_TYPE")
    @POST("fcm/send")
    fun postNotification(@Body notification: PushNotification): Call<ResponseBody>
}