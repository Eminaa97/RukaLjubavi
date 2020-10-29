package com.fit.ba.rukaljubavi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.fit.ba.rukaljubavi.Models.Donator
import com.fit.ba.rukaljubavi.Models.Kategorija
import com.fit.ba.rukaljubavi.Requests.DonatorInsertRequest
import com.fit.ba.rukaljubavi.Services.APIService
import com.fit.ba.rukaljubavi.Services.DonatorService
import com.fit.ba.rukaljubavi.Services.KategorijaService
import kotlinx.android.synthetic.main.activity_donator_profil.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DonatorProfilActivity : AppCompatActivity() {

    private val service = APIService.buildService(DonatorService::class.java)
    var donator: Donator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donator_profil)

        loadDonator()

        btnAzurirajPodatke.setOnClickListener {
            val intent = Intent(this,AzurirajPodatkeActivity::class.java)
            intent.putExtra("ACTIVITY","DonatorProfilActivity")
            intent.putExtra("DONATOR", donator)
            startActivity(intent)
        }
    }

    private fun loadDonator() {
        var loading = LoadingDialog(this@DonatorProfilActivity)
        loading.startLoadingDialog()
        val requestCall = service.getById(APIService.loggedUserId)
        requestCall.enqueue(object : Callback<Donator> {
            override fun onFailure(call: Call<Donator>, t: Throwable) {
                Toast.makeText(this@DonatorProfilActivity,"Server error", Toast.LENGTH_SHORT).show()
                loading.stopDialog()
            }

            override fun onResponse(call: Call<Donator>, response: Response<Donator>) {
                if(response.isSuccessful){
                    donator = response.body()
                    txtImePrezime.text = "${donator!!.ime} ${donator!!.prezime}"
                    txtJMBG.text = donator!!.jmbg
                    txtEmail.text = donator!!.email
                    txtTelefon.text = donator!!.telefon
                    txtAdresa.text = donator!!.adresa
                    txtGrad.text = donator!!.mjestoPrebivalista
                    txtBrojDonacija2.text = donator!!.BrojDonacija.toString()
                    var dan = donator!!.datumRegistracije.substring(8,10)
                    var mjesec = donator!!.datumRegistracije.substring(5,7)
                    var godina = donator!!.datumRegistracije.substring(0,4)
                    txtFooter.text = "Korisnik aplikacije od: $dan.$mjesec.$godina"
                }
                loading.stopDialog()
            }
        })
    }
}