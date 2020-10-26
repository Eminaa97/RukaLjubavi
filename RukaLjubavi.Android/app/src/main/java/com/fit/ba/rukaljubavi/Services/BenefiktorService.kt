package com.fit.ba.rukaljubavi.Services

import com.fit.ba.rukaljubavi.Models.Benefiktor
import com.fit.ba.rukaljubavi.Models.Donator
import com.fit.ba.rukaljubavi.Requests.BenefiktorInsertRequest
import com.fit.ba.rukaljubavi.Requests.DonatorInsertRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BenefiktorService {
    @GET("korisnici/benefiktori")
    fun get(): Call<List<Benefiktor>>
    @GET("korisnici/benefiktori/{id}")
    fun getById(@Path("id") id: Int?): Call<Benefiktor>
    @POST("korisnici/benefiktori/register")
    fun send(@Body newItem: BenefiktorInsertRequest): Call<Unit>
}