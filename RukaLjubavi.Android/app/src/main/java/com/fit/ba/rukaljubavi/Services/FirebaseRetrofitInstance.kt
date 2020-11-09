package com.fit.ba.rukaljubavi.Services

import android.util.Log
import android.widget.Toast
import com.fit.ba.rukaljubavi.Helper.FirebaseConstants.Companion.BASE_URL
import com.fit.ba.rukaljubavi.Models.PushNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

object FirebaseRetrofitInstance {

    val TAG = "Error"
    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttp = OkHttpClient.Builder()
        .addInterceptor(logger)
        .addInterceptor{ chain ->
            val newRequest = chain.request().newBuilder()
                .build()
            chain.proceed(newRequest)
        }

    private val builder = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

    private val retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T{
        return retrofit.create(serviceType)
    }

    fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            var service = buildService(PushNotificationService::class.java)
            val requestCall = service.postNotification(notification)
            requestCall.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d(TAG, call.toString())
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if(response.isSuccessful){
                        Log.d(TAG, "Response: ${response.body()}")
                    }
                    else{
                        Log.d(TAG, "Error: ${response.errorBody()}")
                    }
                }
            })
        } catch(e: Exception) {
            Log.d(TAG, e.toString())
        }
    }
}