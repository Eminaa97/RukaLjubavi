package com.fit.ba.rukaljubavi

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.fit.ba.rukaljubavi.Models.Benefiktor
import com.fit.ba.rukaljubavi.Models.Grad
import com.fit.ba.rukaljubavi.Models.Kategorija
import com.fit.ba.rukaljubavi.Requests.BenefiktorInsertRequest
import com.fit.ba.rukaljubavi.Requests.DonacijaInsertRequest
import com.fit.ba.rukaljubavi.Services.APIService
import com.fit.ba.rukaljubavi.Services.BenefiktorService
import com.fit.ba.rukaljubavi.Services.DonacijaService
import com.fit.ba.rukaljubavi.Services.KategorijaService
import kotlinx.android.synthetic.main.activity_nova_donacija.*
import kotlinx.android.synthetic.main.activity_registracija_benefiktor.*
import kotlinx.android.synthetic.main.activity_registracija_donator.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NovaDonacijaActivity : AppCompatActivity() {

    private val service = APIService.buildService(DonacijaService::class.java)
    private val serviceKategorije = APIService.buildService(KategorijaService::class.java)
    var kategorije: MutableList<Kategorija>? = arrayListOf()
    var spinner: Spinner? = null
    var benefiktor: Benefiktor? = null
    var donacija: DonacijaInsertRequest = DonacijaInsertRequest()
    var previousActivity: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nova_donacija)
        title = "Nova donacija"

        previousActivity = intent.getStringExtra("ACTIVITY")
        if(previousActivity.equals("BenefiktorProfilActivity")){
            benefiktor = intent.getSerializableExtra("BENEFIKTOR") as Benefiktor
            txtBenefiktor.setText(benefiktor!!.nazivKompanije)
            txtBenefiktor.isFocusable = false
            txtBenefiktor.setTypeface(txtBenefiktor.getTypeface(), Typeface.BOLD)
            donacija.benefiktorId = benefiktor!!.id
        }
        else{
            txtBenefiktor.setOnClickListener {
                val intent = Intent(this@NovaDonacijaActivity, BenefiktoriListaActivity::class.java)
                intent.putExtra("ACTIVITY","NovaDonacijaActivity")
                startActivity(intent)
                finish()
            }
            if(previousActivity.equals("BenefiktoriListaActivity")) {
                benefiktor = intent.getSerializableExtra("BENEFIKTOR") as Benefiktor
                txtBenefiktor.setTypeface(txtBenefiktor.getTypeface(), Typeface.BOLD)
                donacija.benefiktorId = benefiktor!!.id
                txtBenefiktor.setText(benefiktor!!.nazivKompanije)
            }
            txtBenefiktor.isClickable = true
            txtBenefiktor.isFocusable = false
            txtBenefiktor.inputType = InputType.TYPE_NULL
        }
        txtDonator.setText(APIService.naziv)
        txtDonator.isFocusable = false
        donacija.donatorId = APIService.loggedUserId

        loadKategorije()
        spinner = txtKategorija
        spinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var kategorija = p0!!.getItemAtPosition(p2) as Kategorija
                donacija.kategorijaId = kategorija.id
            }
        }

        btnAddDonacija.setOnClickListener {
            sendDonacija()
        }
    }

    private fun sendDonacija() {
        donacija.kolicina = txtKolicina!!.text.toString().toIntOrNull()
        donacija.opis = txtOpis!!.text.toString()

        var error: Boolean = false
        if(donacija.opis.isNullOrBlank()){
            txtOpis.setBackgroundResource(R.drawable.input_field_multiline_error)
            error = true
        }
        else
            txtOpis.setBackgroundResource(R.drawable.input_field_multiline)
        if(donacija.kategorijaId == -1){
            txtKategorija.setBackgroundResource(R.drawable.spiner_field_error)
            error = true
        }
        else
            txtKategorija.setBackgroundResource(R.drawable.spiner_field)
        if(!previousActivity.equals("BenefiktorProfilActivity")) {
            if(donacija.benefiktorId == null){
                txtBenefiktor.setBackgroundResource(R.drawable.input_field_error)
                error = true
            }
            else
                txtBenefiktor.setBackgroundResource(R.drawable.input_field)
        }

        if(!error) {
            val requestCall = service.send(APIService.loggedUserToken, donacija)

            var loading = LoadingDialog(this@NovaDonacijaActivity)
            loading.startLoadingDialog()

            requestCall.enqueue(object : Callback<Unit> {
                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    loading.stopDialog()
                    Toast.makeText(
                        this@NovaDonacijaActivity,
                        "Error: ${t.toString()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@NovaDonacijaActivity,
                            "Zahtjev za donaciju poslan.",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent =
                            Intent(this@NovaDonacijaActivity, DonatorHomePageActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@NovaDonacijaActivity,
                            response.message(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    loading.stopDialog()
                }
            })
        }
    }

    private fun loadKategorije() {
        var loading = LoadingDialog(this@NovaDonacijaActivity)
        loading.startLoadingDialog()
        val requestCall = serviceKategorije.getAll()
        requestCall.enqueue(object : Callback<List<Kategorija>> {
            override fun onFailure(call: Call<List<Kategorija>>, t: Throwable) {
                Toast.makeText(this@NovaDonacijaActivity,"Server error", Toast.LENGTH_SHORT).show()
                loading.stopDialog()
            }

            override fun onResponse(call: Call<List<Kategorija>>, response: Response<List<Kategorija>>) {
                if(response.isSuccessful){
                    var list = response.body()
                    kategorije = list!!.toMutableList()
                    kategorije!!.add(0,Kategorija(-1,"Odaberite kategoriju"))
                    var adapter = object : ArrayAdapter<Kategorija>(this@NovaDonacijaActivity,R.layout.spinner_list_item,kategorije!!){
                        override fun isEnabled(position: Int): Boolean {
                            return position != 0
                        }

                        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                            val view = super.getDropDownView(position, convertView, parent)
                            val tv = view as TextView
                            if (position === 0) {
                                tv.setTextColor(Color.GRAY)
                            } else {
                                tv.setTextColor(Color.BLACK)
                            }
                            return view
                        }
                    }
                    adapter.setDropDownViewResource(R.layout.spinner_item)
                    spinner!!.adapter = adapter
                }
                loading.stopDialog()
            }
        })
    }
}