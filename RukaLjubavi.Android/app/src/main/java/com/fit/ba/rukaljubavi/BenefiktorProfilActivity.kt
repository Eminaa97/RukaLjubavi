package com.fit.ba.rukaljubavi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.fit.ba.rukaljubavi.Models.Benefiktor
import com.fit.ba.rukaljubavi.Models.Donator
import com.fit.ba.rukaljubavi.Requests.BenefiktorInsertRequest
import com.fit.ba.rukaljubavi.Services.APIService
import com.fit.ba.rukaljubavi.Services.BenefiktorService
import com.fit.ba.rukaljubavi.Services.DonatorService
import kotlinx.android.synthetic.main.activity_benefiktor_profil.*
import kotlinx.android.synthetic.main.activity_donator_profil.*
import kotlinx.android.synthetic.main.activity_donator_profil.btnAzurirajPodatke
import kotlinx.android.synthetic.main.activity_donator_profil.txtAdresa
import kotlinx.android.synthetic.main.activity_donator_profil.txtEmail
import kotlinx.android.synthetic.main.activity_donator_profil.txtFooter
import kotlinx.android.synthetic.main.activity_donator_profil.txtGrad
import kotlinx.android.synthetic.main.activity_donator_profil.txtTelefon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BenefiktorProfilActivity : AppCompatActivity() {

    private val service = APIService.buildService(BenefiktorService::class.java)
    var benefiktor: Benefiktor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_benefiktor_profil)

        var previousActivity = intent.getStringExtra("ACTIVITY")
        if(previousActivity.equals("BenefiktoriListaActivity")){
            btnBenefiktorProfil.text = "Doniraj"
            benefiktor = intent.getSerializableExtra("BENEFIKTOR") as Benefiktor
            initBenefiktorData()
            btnBenefiktorProfil.setOnClickListener {
                val intent = Intent(this, NovaDonacijaActivity::class.java)
                intent.putExtra("BENEFIKTOR",benefiktor)
                intent.putExtra("ACTIVITY","BenefiktorProfilActivity")
                startActivity(intent)
            }
        }
        else {
            loadBenefiktor()
            btnBenefiktorProfil.setOnClickListener {
                val intent = Intent(this,AzurirajPodatkeActivity::class.java)
                intent.putExtra("ACTIVITY","BenefiktorProfilActivity")
                intent.putExtra("BENEFIKTOR", benefiktor)
                startActivity(intent)
            }
        }
    }

    private fun initBenefiktorData(){
        txtNazivKompanije.text = benefiktor!!.nazivKompanije
        txtPDVBroj.text = benefiktor!!.pdvbroj
        txtEmail.text = benefiktor!!.email
        txtTelefon.text = benefiktor!!.telefon
        txtAdresa.text = benefiktor!!.adresa
        txtGrad.text = benefiktor!!.mjestoPrebivalista
        txtBrojDonacija.text = benefiktor!!.BrojDonacija.toString()
        var dan = benefiktor!!.datumRegistracije.substring(8,10)
        var mjesec = benefiktor!!.datumRegistracije.substring(5,7)
        var godina = benefiktor!!.datumRegistracije.substring(0,4)
        txtFooter.text = "Korisnik aplikacije od: $dan.$mjesec.$godina"
    }

    private fun loadBenefiktor() {
        var loading = LoadingDialog(this@BenefiktorProfilActivity)
        loading.startLoadingDialog()
        val requestCall = service.getById(APIService.loggedUserId)
        requestCall.enqueue(object : Callback<Benefiktor> {
            override fun onFailure(call: Call<Benefiktor>, t: Throwable) {
                Toast.makeText(this@BenefiktorProfilActivity,"Server error", Toast.LENGTH_SHORT).show()
                loading.stopDialog()
            }

            override fun onResponse(call: Call<Benefiktor>, response: Response<Benefiktor>) {
                if(response.isSuccessful){
                    benefiktor = response.body()
                    initBenefiktorData()
                }
                loading.stopDialog()
            }
        })
    }
}