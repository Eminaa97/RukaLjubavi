package com.fit.ba.rukaljubavi.Services

import com.fit.ba.rukaljubavi.Models.Donator
import com.fit.ba.rukaljubavi.Models.Drzava
import com.fit.ba.rukaljubavi.Requests.DonatorInsertRequest
import retrofit2.Call
import retrofit2.http.*

interface DonatorService {
    @GET("korisnici/donatori/{id}")
    fun getById(@Path("id") id: Int?): Call<Donator>
    @POST("korisnici/donatori/register")
    fun send(@Body newItem: DonatorInsertRequest): Call<Unit>
}