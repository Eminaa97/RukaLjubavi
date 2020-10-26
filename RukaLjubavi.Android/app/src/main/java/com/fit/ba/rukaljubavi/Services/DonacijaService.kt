package com.fit.ba.rukaljubavi.Services

import com.fit.ba.rukaljubavi.Requests.DonacijaInsertRequest
import com.fit.ba.rukaljubavi.Requests.DonatorInsertRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface DonacijaService {
    @POST("Donacija")
    fun send(@Header("Authorization") authorization: String?, @Body newItem: DonacijaInsertRequest): Call<Unit>
}