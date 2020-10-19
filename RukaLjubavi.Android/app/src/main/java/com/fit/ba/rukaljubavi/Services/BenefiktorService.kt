package com.fit.ba.rukaljubavi.Services

import com.fit.ba.rukaljubavi.Requests.BenefiktorInsertRequest
import com.fit.ba.rukaljubavi.Requests.DonatorInsertRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface BenefiktorService {
    @POST("korisnici/benefiktori/register")
    fun send(@Body newItem: BenefiktorInsertRequest): Call<Unit>
}