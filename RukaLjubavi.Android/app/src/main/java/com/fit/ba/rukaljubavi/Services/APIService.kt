package com.fit.ba.rukaljubavi.Services

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIService {
    private const val URL = "https://s93.wrd.app.fit.ba/api/"

    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    var loggedUserId: Int? = null
    var loggedUserType: Int? = null
    var naziv: String? = null
    var loggedUserToken: String = ""

    private val okHttp = OkHttpClient.Builder()
        .addInterceptor(logger)
        .addInterceptor{ chain ->
            val newRequest = chain.request().newBuilder()
                .build()
            chain.proceed(newRequest)
        }

    private val builder = Retrofit.Builder().baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

    private val retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T{
        return retrofit.create(serviceType)
    }
}