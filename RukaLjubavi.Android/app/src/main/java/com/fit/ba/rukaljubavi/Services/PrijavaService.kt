package com.fit.ba.rukaljubavi.Services

import com.fit.ba.rukaljubavi.Requests.BenefiktorInsertRequest
import com.fit.ba.rukaljubavi.Requests.PrijavaRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PrijavaService {
    @POST("korisnici/login")
    fun login(@Body login: PrijavaRequest): Call<Unit>
}