package com.fit.ba.rukaljubavi

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.view.marginTop
import com.fit.ba.rukaljubavi.Models.Donacija
import com.fit.ba.rukaljubavi.Models.Notification
import com.fit.ba.rukaljubavi.Models.PushNotification
import com.fit.ba.rukaljubavi.Models.StatusDonacije
import com.fit.ba.rukaljubavi.Services.APIService
import com.fit.ba.rukaljubavi.Services.DonacijaService
import com.fit.ba.rukaljubavi.Services.FirebaseRetrofitInstance
import kotlinx.android.synthetic.main.activity_donacija_detalji.*
import kotlinx.android.synthetic.main.activity_donacija_detalji.txtBenefiktor
import kotlinx.android.synthetic.main.activity_donacija_detalji.txtDonator
import kotlinx.android.synthetic.main.activity_donacija_detalji.txtKategorija
import kotlinx.android.synthetic.main.activity_donacija_detalji.txtOpis
import kotlinx.android.synthetic.main.activity_nova_donacija.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DonacijaDetaljiActivity : AppCompatActivity() {

    val service = APIService.buildService(DonacijaService::class.java)
    var donacije: Donacija? = null
    var previousActivity: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donacija_detalji)
        title = "Detalji donacije"
        donacije = intent.getSerializableExtra("DONACIJA") as Donacija

        btnDetaljiDonirajOdbij.visibility = View.GONE;
        btnDetaljiDoniraj.visibility = View.GONE;

        previousActivity = intent.getStringExtra("ACTIVITY")
        if(previousActivity.equals("AktivneDonacijeActivity") || previousActivity.equals("ZahtjeviBenefiktoraListaActivity")){
            btnDetaljiDoniraj.text = "Preuzmi"
            btnDetaljiDoniraj.visibility = View.VISIBLE;
            btnDetaljiDonirajOdbij.visibility = View.GONE;
        }

        if(!previousActivity.equals("VaseDonacijeActivity")){
            btnDetaljiDoniraj.setOnClickListener {
                preuzmiDonaciju()
            }
        }

        if(previousActivity.equals("ZahtjeviDonatoraActivity")){
            btnDetaljiDoniraj.text = "Prihvati"
            btnDetaljiDoniraj.visibility = View.VISIBLE;
            btnDetaljiDonirajOdbij.visibility = View.VISIBLE;

            btnDetaljiDonirajOdbij.setOnClickListener {
                odbijDonaciju()
            }
        }

        if(donacije!!.donatorId != 0){
            txtDonator.setText("""${donacije!!.donatorIme} ${donacije!!.donatorPrezime}""")
        }
        else{
            txtDonator.setText("N/A")
        }
        if(donacije!!.benefiktorId != 0){
            txtBenefiktor.setText(donacije!!.benefiktorNazivKompanije)
        }
        else{
            txtBenefiktor.setText("N/A")
        }

        if(APIService.loggedUserType == 2) {
            txtDonator.setOnClickListener {
                val intent = Intent(this, DonatorProfilActivity::class.java)
                intent.putExtra("ACTIVITY", "DonacijaDetaljiActivity")
                intent.putExtra("DONATOR_ID", donacije!!.donatorId)
                startActivity(intent)
            }

            if(donacije!!.status.equals(StatusDonacije.Prihvacena.name) ||
                donacije!!.status.equals(StatusDonacije.Zavrsena.name) ||
                donacije!!.status.equals(StatusDonacije.U_toku.name)){

                btnDetaljiDoniraj.visibility = View.VISIBLE;
                btnDetaljiDoniraj.text = "Promjeni status"
                btnDetaljiDoniraj.setOnClickListener {
                    PromjeniStatusDialog(this@DonacijaDetaljiActivity, donacijaId = donacije!!.id, trenutniStatus = donacije!!.status).startPromjeniStatusDialog()
                }
            }
        }

        if(donacije!!.status.equals(StatusDonacije.Zavrsena.name)){
            btnRatingDialogVasaOcjena.setOnClickListener {
                RatingDialog(this@DonacijaDetaljiActivity, donacije!!.id).loadDialog()
            }
            if(APIService.loggedUserType == 1){
                txtOcjenaDon2.text = "Ocjena benefiktora"
            }
            btnRatingDialogOcjena.setOnClickListener {
                RatingDialog(this@DonacijaDetaljiActivity, donacije!!.id, false).loadDialog()
            }
        }
        else{
            btnRatingDialogVasaOcjena.visibility = View.GONE;
            btnRatingDialogOcjena.visibility = View.GONE;
            txtOcjenaDon.visibility = View.GONE;
            txtOcjenaDon2.visibility = View.GONE;
            val param1 = txtDonator.layoutParams as ViewGroup.MarginLayoutParams
            val param2 = textView35.layoutParams as ViewGroup.MarginLayoutParams
            param1.setMargins(35,20,0,0)
            param2.setMargins(0,20,35,0)
        }

        txtKategorija.setText(donacije!!.nazivKategorije)
        txtStatus.setText(donacije!!.status)
        txtOpis.setText(donacije!!.opis)
        var dan = donacije!!.datumVrijeme.substring(8,10)
        var mjesec = donacije!!.datumVrijeme.substring(5,7)
        var godina = donacije!!.datumVrijeme.substring(0,4)
        txtDatum.text = "$dan.$mjesec.$godina"
    }

    private fun odbijDonaciju() {
        val requestCall = service.changeStatus(APIService.loggedUserToken, donacijaId = donacije!!.id, statusDonacije = StatusDonacije.Odbijena)

        var loading = LoadingDialog(this@DonacijaDetaljiActivity)
        loading.startLoadingDialog()

        requestCall.enqueue(object : Callback<Unit> {
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                loading.stopDialog()
                Toast.makeText(
                    this@DonacijaDetaljiActivity,
                    "Error: ${t.toString()}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@DonacijaDetaljiActivity,
                        "Donacija odbijena.",
                        Toast.LENGTH_SHORT
                    ).show()
                    val requestCall = serviceDonacije.get(StatusDonacije = StatusDonacije.Na_cekanju)
                    requestCall.enqueue(object : Callback<List<Donacija>> {
                        override fun onFailure(call: Call<List<Donacija>>, t: Throwable) {
                            Toast.makeText(this@DonacijaDetaljiActivity,"Error: ${t.toString()}", Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(call: Call<List<Donacija>>, response: Response<List<Donacija>>) {
                            if(response.isSuccessful){
                                val list = response.body()!!
                                myAdapterZahtjeviDonatora.submitList(list)
                                myAdapterZahtjeviDonatora.notifyDataSetChanged()
                                finish()
                                loading.stopDialog()

                                FirebaseRetrofitInstance.sendNotification(
                                    PushNotification(
                                        Notification("Ruka Ljubavi", "Benefiktor ${APIService.naziv} je odbio vašu donaciju kategorije ${donacije!!.nazivKategorije}.", DonatorHomePageActivity::class.java.name),
                                        "/topics/donator")
                                )
                            }
                        }
                    })
                } else {
                    Toast.makeText(
                        this@DonacijaDetaljiActivity,
                        response.message(),
                        Toast.LENGTH_SHORT
                    ).show()
                    loading.stopDialog()
                }
            }
        })
    }

    private fun preuzmiDonaciju() {
        val requestCall = service.acceptStatus(APIService.loggedUserToken, donacije!!.id, APIService!!.loggedUserId)

        var loading = LoadingDialog(this@DonacijaDetaljiActivity)
        loading.startLoadingDialog()

        requestCall.enqueue(object : Callback<Unit> {
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                loading.stopDialog()
                Toast.makeText(
                    this@DonacijaDetaljiActivity,
                    "Error: ${t.toString()}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@DonacijaDetaljiActivity, "Donacija prihvaćena.", Toast.LENGTH_SHORT).show()

                    if(previousActivity == "ZahtjeviDonatoraActivity"){
                        val requestCall = serviceDonacije.get(StatusDonacije = StatusDonacije.Na_cekanju)
                        requestCall.enqueue(object : Callback<List<Donacija>> {
                            override fun onFailure(call: Call<List<Donacija>>, t: Throwable) {
                                Toast.makeText(this@DonacijaDetaljiActivity,"Error: ${t.toString()}", Toast.LENGTH_SHORT).show()
                            }

                            override fun onResponse(call: Call<List<Donacija>>, response: Response<List<Donacija>>) {
                                if(response.isSuccessful){
                                    val list = response.body()!!
                                    myAdapterZahtjeviDonatora.submitList(list)
                                    myAdapterZahtjeviDonatora.notifyDataSetChanged()
                                    finish()
                                    loading.stopDialog()

                                    FirebaseRetrofitInstance.sendNotification(
                                        PushNotification(
                                            Notification("Ruka Ljubavi", "Benefiktor ${APIService.naziv} je prihvatio vašu donaciju kategorije ${donacije!!.nazivKategorije}.", DonatorHomePageActivity::class.java.name),
                                            "/topics/donator")
                                    )
                                }
                            }
                        })
                    }
                    else if(previousActivity == "ZahtjeviBenefiktoraListaActivity"){
                        val requestCall = serviceDonacije.get(isZahtjevZaDonatora = true)
                        requestCall.enqueue(object : Callback<List<Donacija>> {
                            override fun onFailure(call: Call<List<Donacija>>, t: Throwable) {
                                Toast.makeText(this@DonacijaDetaljiActivity,"Error: ${t.toString()}", Toast.LENGTH_SHORT).show()
                            }

                            override fun onResponse(call: Call<List<Donacija>>, response: Response<List<Donacija>>) {
                                if(response.isSuccessful){
                                    val list = response.body()!!
                                    myAdapterZahtjeviBenefiktoraLista.submitList(list)
                                    myAdapterZahtjeviBenefiktoraLista.notifyDataSetChanged()
                                    finish()
                                    loading.stopDialog()

                                    FirebaseRetrofitInstance.sendNotification(
                                        PushNotification(
                                            Notification("Ruka Ljubavi", "Donator ${APIService.naziv} je preuzeo vašu donaciju kategorije ${donacije!!.nazivKategorije}.", BenefiktorHomePageActivity::class.java.name),
                                            "/topics/benefiktor")
                                    )
                                }
                            }
                        })
                    }
                    else if(previousActivity == "AktivneDonacijeActivity"){
                        val requestCall = serviceDonacije.get(isZahtjevZaBenefiktora = true)
                        requestCall.enqueue(object : Callback<List<Donacija>> {
                            override fun onFailure(call: Call<List<Donacija>>, t: Throwable) {
                                Toast.makeText(this@DonacijaDetaljiActivity,"Error: ${t.toString()}", Toast.LENGTH_SHORT).show()
                            }

                            override fun onResponse(call: Call<List<Donacija>>, response: Response<List<Donacija>>) {
                                if(response.isSuccessful){
                                    val list = response.body()!!
                                    myAdapterAktivneDonacije.submitList(list)
                                    myAdapterAktivneDonacije.notifyDataSetChanged()
                                    finish()
                                    loading.stopDialog()

                                    FirebaseRetrofitInstance.sendNotification(
                                        PushNotification(
                                            Notification("Ruka Ljubavi", "Benefiktor ${APIService.naziv} je preuzeo vašu donaciju kategorije ${donacije!!.nazivKategorije}.", BenefiktorHomePageActivity::class.java.name),
                                            "/topics/donator")
                                    )
                                }
                            }
                        })
                    }
                } else {
                    Toast.makeText(
                        this@DonacijaDetaljiActivity,
                        "Oznacili ste da vam kategorija donacije nije potrebna.",
                        Toast.LENGTH_SHORT
                    ).show()
                    loading.stopDialog()
                }
            }
        })
    }
}