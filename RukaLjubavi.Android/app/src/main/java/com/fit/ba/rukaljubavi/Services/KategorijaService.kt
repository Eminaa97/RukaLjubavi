package com.fit.ba.rukaljubavi.Services

import com.fit.ba.rukaljubavi.Models.Kategorija
import retrofit2.Call
import retrofit2.http.GET

interface KategorijaService {
    @GET("Kategorija")
    fun getAll(): Call<List<Kategorija>>
}