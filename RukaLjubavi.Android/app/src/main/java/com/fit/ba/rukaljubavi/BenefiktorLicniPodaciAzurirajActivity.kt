package com.fit.ba.rukaljubavi

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.fit.ba.rukaljubavi.Models.Benefiktor
import com.fit.ba.rukaljubavi.Models.Donator
import com.fit.ba.rukaljubavi.Models.Grad
import com.fit.ba.rukaljubavi.Requests.BenefiktorUpdateRequest
import com.fit.ba.rukaljubavi.Requests.DonatorUpdateRequest
import com.fit.ba.rukaljubavi.Services.APIService
import com.fit.ba.rukaljubavi.Services.BenefiktorService
import com.fit.ba.rukaljubavi.Services.DonatorService
import com.fit.ba.rukaljubavi.Services.GradService
import kotlinx.android.synthetic.main.activity_benefiktor_licni_podaci_azuriraj.*
import kotlinx.android.synthetic.main.activity_benefiktor_licni_podaci_azuriraj.btnBack4
import kotlinx.android.synthetic.main.activity_benefiktor_licni_podaci_azuriraj.txtRegAdresa
import kotlinx.android.synthetic.main.activity_benefiktor_licni_podaci_azuriraj.txtRegEmail
import kotlinx.android.synthetic.main.activity_benefiktor_licni_podaci_azuriraj.txtRegTelefon
import kotlinx.android.synthetic.main.activity_donator_licni_podaci_azuriraj.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BenefiktorLicniPodaciAzurirajActivity : AppCompatActivity() {

    private val serviceGradovi = APIService.buildService(GradService::class.java)
    private val service = APIService.buildService(BenefiktorService::class.java)
    var benefiktor: Benefiktor? = null
    var benefiktorRequest = BenefiktorUpdateRequest()
    var spinnerMjestoPrebivalista: Spinner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_benefiktor_licni_podaci_azuriraj)

        benefiktor = intent.getSerializableExtra("BENEFIKTOR") as Benefiktor
        txtRegNazivKompanije.setText(benefiktor!!.nazivKompanije)
        txtRegAdresa.setText(benefiktor!!.adresa)
        txtRegEmail.setText(benefiktor!!.email)
        txtRegTelefon.setText(benefiktor!!.telefon)
        spinnerMjestoPrebivalista = spnBenLokacija
        loadGradovi()
        spinnerMjestoPrebivalista!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var grad = p0!!.getItemAtPosition(p2) as Grad
                benefiktorRequest.mjestoPrebivalistaId = grad.id
            }
        }

        btnBack4.setOnClickListener {
            finish()
        }

        btnBenefiktorAzuriraj.setOnClickListener {
            updateBenefiktor()
        }
    }

    private fun updateBenefiktor() {
        benefiktorRequest.nazivKompanije = txtRegNazivKompanije!!.text.toString()
        benefiktorRequest.adresa = txtRegAdresa!!.text.toString()
        benefiktorRequest.email = txtRegEmail!!.text.toString()
        benefiktorRequest.telefon = txtRegTelefon!!.text.toString()
        benefiktorRequest.korisnikId = benefiktor!!.korisnikId

        var error: Boolean = false

        if(benefiktorRequest.nazivKompanije.isNullOrBlank()){
            txtRegNazivKompanije.setBackgroundResource(R.drawable.input_field_error)
            error = true
        }
        else
            txtRegNazivKompanije.setBackgroundResource(R.drawable.input_field)
        if(benefiktorRequest.adresa.isNullOrBlank()){
            txtRegAdresa.setBackgroundResource(R.drawable.input_field_error)
            error = true
        }
        else
            txtRegAdresa.setBackgroundResource(R.drawable.input_field)
        if(benefiktorRequest.email.isNullOrBlank()){
            txtRegEmail.setBackgroundResource(R.drawable.input_field_error)
            error = true
        }
        else{
            if(!TextUtils.isEmpty(benefiktorRequest.email) && android.util.Patterns.EMAIL_ADDRESS.matcher(benefiktorRequest.email).matches()){
                txtRegEmail.setBackgroundResource(R.drawable.input_field)
            }
            else{
                error = true
                txtRegEmail.setBackgroundResource(R.drawable.input_field_error)
            }
        }
        if(benefiktorRequest.telefon.isNullOrBlank()){
            txtRegTelefon.setBackgroundResource(R.drawable.input_field_error)
            error = true
        }
        else
            txtRegTelefon.setBackgroundResource(R.drawable.input_field)
        if(benefiktorRequest.mjestoPrebivalistaId == -1){
            spnBenLokacija.setBackgroundResource(R.drawable.spiner_field_error)
            error = true
        }
        else
            spnBenLokacija.setBackgroundResource(R.drawable.spiner_field)

        if(!error) {
            sendBenefiktor()
        }
    }

    private fun sendBenefiktor() {
        val requestCall = service.update(APIService.loggedUserToken ,benefiktorRequest)

        var loading = LoadingDialog(this@BenefiktorLicniPodaciAzurirajActivity)
        loading.startLoadingDialog()

        requestCall.enqueue(object : Callback<Unit> {
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                loading.stopDialog()
                Toast.makeText(this@BenefiktorLicniPodaciAzurirajActivity,"Error: ${t.toString()}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(response.isSuccessful){
                    Toast.makeText(this@BenefiktorLicniPodaciAzurirajActivity,"Uspješno ažuriranje.", Toast.LENGTH_SHORT).show()
                    finish()
                }
                else{
                    Toast.makeText(this@BenefiktorLicniPodaciAzurirajActivity,response.message(), Toast.LENGTH_SHORT).show()
                }
                loading.stopDialog()
            }
        })
    }

    private fun loadGradovi() {
        var loading = LoadingDialog(this@BenefiktorLicniPodaciAzurirajActivity)
        loading.startLoadingDialog()
        val requestCall = serviceGradovi.getAll()
        requestCall.enqueue(object : Callback<List<Grad>> {
            override fun onFailure(call: Call<List<Grad>>, t: Throwable) {
                Toast.makeText(this@BenefiktorLicniPodaciAzurirajActivity,"Server error", Toast.LENGTH_SHORT).show()
                loading.stopDialog()
            }

            override fun onResponse(call: Call<List<Grad>>, response: Response<List<Grad>>) {
                if(response.isSuccessful){
                    var list = response.body()
                    var gradoviMP = list!!.toMutableList()
                    initMjestoPrebivalistaSpinner(gradoviMP)
                }
                loading.stopDialog()
            }
        })
    }

    private fun initMjestoPrebivalistaSpinner(gradovi: MutableList<Grad>?){
        gradovi!!.add(0, Grad(-1,"Lokacija"))
        var adapter = object : ArrayAdapter<Grad>(this@BenefiktorLicniPodaciAzurirajActivity,R.layout.spinner_list_item,gradovi!!){
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
        spinnerMjestoPrebivalista!!.adapter = adapter
        var index: Int = getIndex(spinnerMjestoPrebivalista!!, benefiktor!!.mjestoPrebivalista)
        spinnerMjestoPrebivalista!!.setSelection(index)
        benefiktorRequest.mjestoPrebivalistaId = benefiktor!!.mjestoPrebivalistaId
    }

    private fun getIndex(spinner: Spinner, myString: String): Int {
        var index = 0
        for (i in 0 until spinner.count) {
            val temp: String = spinner.getItemAtPosition(i).toString()
            if (temp == myString) {
                index = i
            }
        }
        return index
    }
}