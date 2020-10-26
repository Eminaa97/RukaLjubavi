package com.fit.ba.rukaljubavi.Services

import com.fit.ba.rukaljubavi.Models.Kategorija
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface KategorijaService {
    @GET("Kategorija")
    fun getAll(): Call<List<Kategorija>>
    @GET("Kategorija")
    fun getKategorijeByUser(@Query("DonatorId") donatorId: Int?, @Query("BenefiktorId") benefiktorId: Int?): Call<List<Kategorija>>
}