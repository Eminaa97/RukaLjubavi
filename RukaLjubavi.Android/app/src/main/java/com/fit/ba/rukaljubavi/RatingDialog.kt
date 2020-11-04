package com.fit.ba.rukaljubavi

import android.app.Activity
import android.app.AlertDialog
import android.widget.*
import com.fit.ba.rukaljubavi.Models.Kategorija
import com.fit.ba.rukaljubavi.Models.OcjenaDonacije
import com.fit.ba.rukaljubavi.Requests.OcjenaDonacijeInsertRequest
import com.fit.ba.rukaljubavi.Services.APIService
import com.fit.ba.rukaljubavi.Services.KategorijaService
import com.fit.ba.rukaljubavi.Services.OcjenaDonacijeService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RatingDialog(var activity: Activity, var donacijaId: Int) {

    private val service = APIService.buildService(OcjenaDonacijeService::class.java)
    var dialog: AlertDialog? = null
    var ocjenaDonacije: OcjenaDonacijeInsertRequest = OcjenaDonacijeInsertRequest()
    var ratingBarPovjerljivost: RatingBar? = null
    var ratingBarBrzinaDostavljanja: RatingBar? = null
    var ratingBarPostizanjeDogovora: RatingBar? = null
    var txtOpis: EditText? = null
    var isPostojiOcjena: Boolean = false
    var ocjenaDonacijeId: Int? = null

    fun loadDialog(){
        var builder = AlertDialog.Builder(activity)
        var inflater = activity.layoutInflater
        var dialogView = inflater.inflate(R.layout.dialog_rating_donacije,null)

        builder.setView(dialogView)
        dialog = builder.create()
        dialog!!.show()

        loadPostojecaOcjena()

        var btnOcijeni = dialogView.findViewById<TextView>(R.id.btnOcijeni)
        var btnOdustani = dialogView.findViewById<TextView>(R.id.btnOdustani)
        ratingBarPovjerljivost = dialogView.findViewById<RatingBar>(R.id.ratingBarPovjerljivost)
        ratingBarBrzinaDostavljanja = dialogView.findViewById<RatingBar>(R.id.ratingBarBrzinaDostavljanja)
        ratingBarPostizanjeDogovora = dialogView.findViewById<RatingBar>(R.id.ratingBarPostizanjeDogovora)
        txtOpis = dialogView.findViewById<EditText>(R.id.txtOpis)

        ocjenaDonacije!!.donacijaId = donacijaId
        ocjenaDonacije!!.ocjenjivacTipKorisnika = APIService!!.loggedUserType
        ocjenaDonacije!!.korisnikId = APIService!!.loggedId

        btnOdustani.setOnClickListener {
            dialog!!.dismiss()
        }

        btnOcijeni.setOnClickListener {
            ocijeni()
        }
    }

    private fun loadPostojecaOcjena() {
        var loading = LoadingDialog(activity)
        loading.startLoadingDialog()

        val requestCall = service.getAll(KorisnikId = APIService!!.loggedId, DonacijaId = donacijaId)
        requestCall.enqueue(object : Callback<List<OcjenaDonacije>> {
            override fun onFailure(call: Call<List<OcjenaDonacije>>, t: Throwable) {
                Toast.makeText(activity,"Server error", Toast.LENGTH_SHORT).show()
                loading.stopDialog()
            }

            override fun onResponse(call: Call<List<OcjenaDonacije>>, response: Response<List<OcjenaDonacije>>) {
                if(response.isSuccessful){
                    var list = response.body()
                    if(list!!.isNotEmpty()){
                        isPostojiOcjena = true
                        ocjenaDonacijeId = list.last().id
                        txtOpis!!.setText(list.last().komentar)
                        ratingBarPovjerljivost!!.rating = list.last().povjerljivost.toFloat()
                        ratingBarBrzinaDostavljanja!!.rating = list.last().brzinaDostavljanja.toFloat()
                        ratingBarPostizanjeDogovora!!.rating = list.last().postivanjeDogovora.toFloat()
                    }
                }
                loading.stopDialog()
            }
        })
    }

    private fun ocijeni() {
        var loading = LoadingDialog(activity)
        loading.startLoadingDialog()

        ocjenaDonacije!!.komentar = txtOpis?.text.toString()
        ocjenaDonacije!!.povjerljivost = ratingBarPovjerljivost!!.rating.toInt()
        ocjenaDonacije!!.brzinaDostavljanja = ratingBarBrzinaDostavljanja!!.rating.toInt()
        ocjenaDonacije!!.postivanjeDogovora = ratingBarPostizanjeDogovora!!.rating.toInt()

        val requestCall = if(!isPostojiOcjena)
            service.send(APIService.loggedUserToken, ocjenaDonacije!!)
        else
            service.update(APIService.loggedUserToken, ocjenaDonacijeId!!, ocjenaDonacije!!)

        requestCall.enqueue(object : Callback<Unit> {
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Toast.makeText(activity,"Server error", Toast.LENGTH_SHORT).show()
                loading.stopDialog()
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(response.isSuccessful){
                    Toast.makeText(activity,"Uspješno ocjenjeno.", Toast.LENGTH_SHORT).show()
                    dialog!!.dismiss()
                }
                else{
                    Toast.makeText(activity,response.message(), Toast.LENGTH_SHORT).show()
                }
                loading.stopDialog()
            }
        })
    }
}