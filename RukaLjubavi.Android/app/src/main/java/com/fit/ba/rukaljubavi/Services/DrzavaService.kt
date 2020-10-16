package com.fit.ba.rukaljubavi.Services

import com.fit.ba.rukaljubavi.Models.Drzava
import retrofit2.Call
import retrofit2.http.GET

interface DrzavaService {
    @GET("Drzava")
    fun getAll(): Call<List<Drzava>>
}