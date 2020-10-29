package com.fit.ba.rukaljubavi.Services

import com.fit.ba.rukaljubavi.Models.LogiraniUser
import com.fit.ba.rukaljubavi.Requests.BenefiktorInsertRequest
import com.fit.ba.rukaljubavi.Requests.KategorijeUpdateRequest
import com.fit.ba.rukaljubavi.Requests.LozinkaUpdateRequest
import com.fit.ba.rukaljubavi.Requests.PrijavaRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

interface PrijavaService {
    @POST("korisnici/login")
    fun login(@Body login: PrijavaRequest): Call<LogiraniUser>
    @POST("Korisnici/resetpassword")
    fun resetPassword(@Header("Authorization") authorization: String?, @Body login: LozinkaUpdateRequest): Call<Unit>
    @PATCH("Korisnici/kategorije/update")
    fun updateKategorije(@Header("Authorization") authorization: String?, @Body kategorije: KategorijeUpdateRequest): Call<Unit>
}