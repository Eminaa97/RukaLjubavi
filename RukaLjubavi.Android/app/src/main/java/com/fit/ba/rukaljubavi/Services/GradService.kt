package com.fit.ba.rukaljubavi.Services

import com.fit.ba.rukaljubavi.Models.Drzava
import com.fit.ba.rukaljubavi.Models.Grad
import retrofit2.Call
import retrofit2.http.GET

interface GradService {
    @GET("Grad")
    fun getAll(): Call<List<Grad>>
}