package com.fit.ba.rukaljubavi.Services

import com.fit.ba.rukaljubavi.Models.Donacija
import com.fit.ba.rukaljubavi.Models.StatusDonacije
import com.fit.ba.rukaljubavi.Requests.DonacijaInsertRequest
import retrofit2.Call
import retrofit2.http.*

interface DonacijaService {
    @GET("Donacija")
    fun get(@Query("isZahtjevZaDonatora") isZahtjevZaDonatora: Boolean? = null,
            @Query("KategorijaId") KategorijaId: Int? = null,
            @Query("NazivKompanije") NazivKompanije: String? = null,
            @Query("LokacijaBenefiktorId") LokacijaBenefiktorId: Int? = null,
            @Query("LokacijaDonatorId") LokacijaDonatorId: Int? = null,
            @Query("DonatorId") DonatorId: Int? = null,
            @Query("BenefiktorId") BenefiktorId: Int? = null,
            @Query("StatusDonacije") StatusDonacije: StatusDonacije? = null,
            @Query("isZahtjevZaBenefiktora") isZahtjevZaBenefiktora: Boolean? = null): Call<List<Donacija>>
    @POST("Donacija")
    fun send(@Header("Authorization") authorization: String?, @Body newItem: DonacijaInsertRequest): Call<Unit>
    @POST("PromjeniStatus/{id}")
    fun changeStatus(@Header("Authorization") authorization: String?, @Path("id") donacijaId: Int, statusDonacije: StatusDonacije): Call<Unit>
    @POST("prihvatiDonaciju/{id}")
    fun acceptStatus(@Header("Authorization") authorization: String?, @Path("id") donacijaId: Int?, userId: Int?): Call<Unit>
}