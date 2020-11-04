package com.fit.ba.rukaljubavi.Services

import com.fit.ba.rukaljubavi.Models.Drzava
import com.fit.ba.rukaljubavi.Models.OcjenaDonacije
import com.fit.ba.rukaljubavi.Requests.BenefiktorInsertRequest
import com.fit.ba.rukaljubavi.Requests.DonatorUpdateRequest
import com.fit.ba.rukaljubavi.Requests.OcjenaDonacijeInsertRequest
import retrofit2.Call
import retrofit2.http.*

interface OcjenaDonacijeService {
    @GET("OcjenaDonacije")
    fun getAll(@Query("KorisnikId") KorisnikId: Int?, @Query("DonacijaId") DonacijaId: Int): Call<List<OcjenaDonacije>>
    @POST("OcjenaDonacije")
    fun send(@Header("Authorization") authorization: String?, @Body newItem: OcjenaDonacijeInsertRequest): Call<Unit>
    @PATCH("OcjenaDonacije/{id}")
    fun update(@Header("Authorization") authorization: String?,@Path("id") id:Int, @Body newItem: OcjenaDonacijeInsertRequest): Call<Unit>
}