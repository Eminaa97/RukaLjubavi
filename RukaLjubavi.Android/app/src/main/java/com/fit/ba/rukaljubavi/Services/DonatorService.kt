package com.fit.ba.rukaljubavi.Services

import com.fit.ba.rukaljubavi.Models.Drzava
import com.fit.ba.rukaljubavi.Requests.DonatorInsertRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DonatorService {
    @POST("korisnici/donatori/register")
    fun send(@Body newItem: DonatorInsertRequest): Call<Unit>
}