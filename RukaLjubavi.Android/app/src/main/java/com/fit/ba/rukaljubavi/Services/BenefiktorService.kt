package com.fit.ba.rukaljubavi.Services

import com.fit.ba.rukaljubavi.Models.Benefiktor
import com.fit.ba.rukaljubavi.Models.Donator
import com.fit.ba.rukaljubavi.Requests.BenefiktorInsertRequest
import com.fit.ba.rukaljubavi.Requests.BenefiktorUpdateRequest
import com.fit.ba.rukaljubavi.Requests.DonatorInsertRequest
import com.fit.ba.rukaljubavi.Requests.DonatorUpdateRequest
import retrofit2.Call
import retrofit2.http.*

interface BenefiktorService {
    @GET("korisnici/benefiktori")
    fun get(@Query("nazivKompanije") nazivKompanije: String?, @Query("LokacijaId") LokacijaId: Int?): Call<List<Benefiktor>>
    @GET("korisnici/benefiktori/{id}")
    fun getById(@Path("id") id: Int?): Call<Benefiktor>
    @POST("korisnici/benefiktori/register")
    fun send(@Body newItem: BenefiktorInsertRequest): Call<Unit>
    @PATCH("korisnici/benefiktori/update")
    fun update(@Header("Authorization") authorization: String?, @Body newItem: BenefiktorUpdateRequest): Call<Unit>
}