package com.fit.ba.rukaljubavi.Services

import com.fit.ba.rukaljubavi.Models.Donacija
import com.fit.ba.rukaljubavi.Requests.DonacijaInsertRequest
import com.fit.ba.rukaljubavi.Requests.DonatorInsertRequest
import retrofit2.Call
import retrofit2.http.*

interface DonacijaService {
    @GET("Donacija")
    fun get(@Query("isZahtjevZaDonatora") isZahtjevZaDonatora: Boolean?, @Query("isZahtjevZaBenefiktora") isZahtjevZaBenefiktora: Boolean?): Call<List<Donacija>>
    @POST("Donacija")
    fun send(@Header("Authorization") authorization: String?, @Body newItem: DonacijaInsertRequest): Call<Unit>
}