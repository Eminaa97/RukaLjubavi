package com.fit.ba.rukaljubavi

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.fit.ba.rukaljubavi.Models.Donator
import com.fit.ba.rukaljubavi.Models.Grad
import com.fit.ba.rukaljubavi.Requests.DonatorUpdateRequest
import com.fit.ba.rukaljubavi.Services.APIService
import com.fit.ba.rukaljubavi.Services.DonatorService
import com.fit.ba.rukaljubavi.Services.GradService
import kotlinx.android.synthetic.main.activity_donator_licni_podaci_azuriraj.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DonatorLicniPodaciAzurirajActivity : AppCompatActivity() {

    private val serviceGradovi = APIService.buildService(GradService::class.java)
    private val service = APIService.buildService(DonatorService::class.java)
    var donator: Donator? = null
    var donatorRequest = DonatorUpdateRequest()
    var spinnerMjestoPrebivalista: Spinner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donator_licni_podaci_azuriraj)

        donator = intent.getSerializableExtra("DONATOR") as Donator
        txtRegIme.setText(donator!!.ime)
        txtRegPrezime.setText(donator!!.prezime)
        txtRegAdresa.setText(donator!!.adresa)
        txtRegEmail.setText(donator!!.email)
        txtRegTelefon.setText(donator!!.telefon)
        spinnerMjestoPrebivalista = spnDonMjestoPrebivalista
        loadGradovi()
        spinnerMjestoPrebivalista!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var grad = p0!!.getItemAtPosition(p2) as Grad
                donatorRequest.mjestoPrebivalistaId = grad.id
            }
        }

        btnDonatorAzuriraj.setOnClickListener {
            updateDonator()
        }

        btnBack4.setOnClickListener {
            val intent = Intent(this,AzurirajPodatkeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun updateDonator() {
        donatorRequest.ime = txtRegIme!!.text.toString()
        donatorRequest.prezime = txtRegPrezime!!.text.toString()
        donatorRequest.adresa = txtRegAdresa!!.text.toString()
        donatorRequest.email = txtRegEmail!!.text.toString()
        donatorRequest.telefon = txtRegTelefon!!.text.toString()
        donatorRequest.korisnikId = donator!!.korisnikId

        var error: Boolean = false

        if(donatorRequest.ime.isNullOrBlank()){
            txtRegIme.setBackgroundResource(R.drawable.input_field_error)
            error = true
        }
        else
            txtRegIme.setBackgroundResource(R.drawable.input_field)
        if(donatorRequest.prezime.isNullOrBlank()){
            txtRegPrezime.setBackgroundResource(R.drawable.input_field_error)
            error = true
        }
        else
            txtRegPrezime.setBackgroundResource(R.drawable.input_field)
        if(donatorRequest.adresa.isNullOrBlank()){
            txtRegAdresa.setBackgroundResource(R.drawable.input_field_error)
            error = true
        }
        else
            txtRegAdresa.setBackgroundResource(R.drawable.input_field)
        if(donatorRequest.email.isNullOrBlank()){
            txtRegEmail.setBackgroundResource(R.drawable.input_field_error)
            error = true
        }
        else{
            if(!TextUtils.isEmpty(donatorRequest.email) && android.util.Patterns.EMAIL_ADDRESS.matcher(donatorRequest.email).matches()){
                txtRegEmail.setBackgroundResource(R.drawable.input_field)
            }
            else{
                error = true
                txtRegEmail.setBackgroundResource(R.drawable.input_field_error)
            }
        }
        if(donatorRequest.telefon.isNullOrBlank()){
            txtRegTelefon.setBackgroundResource(R.drawable.input_field_error)
            error = true
        }
        else
            txtRegTelefon.setBackgroundResource(R.drawable.input_field)
        if(donatorRequest.mjestoPrebivalistaId == -1){
            spnDonMjestoPrebivalista.setBackgroundResource(R.drawable.spiner_field_error)
            error = true
        }
        else
            spnDonMjestoPrebivalista.setBackgroundResource(R.drawable.spiner_field)

        if(!error) {
            sendDonator()
        }
    }

    private fun sendDonator() {
        val requestCall = service.update(APIService.loggedUserToken, donatorRequest)

        var loading = LoadingDialog(this@DonatorLicniPodaciAzurirajActivity)
        loading.startLoadingDialog()

        requestCall.enqueue(object : Callback<Unit> {
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                loading.stopDialog()
                Toast.makeText(this@DonatorLicniPodaciAzurirajActivity,"Error: ${t.toString()}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(response.isSuccessful){
                    Toast.makeText(this@DonatorLicniPodaciAzurirajActivity,"Uspješno ažuriranje.", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@DonatorLicniPodaciAzurirajActivity, DonatorProfilActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this@DonatorLicniPodaciAzurirajActivity,response.message(), Toast.LENGTH_SHORT).show()
                }
                loading.stopDialog()
            }
        })
    }

    private fun loadGradovi() {
        var loading = LoadingDialog(this@DonatorLicniPodaciAzurirajActivity)
        loading.startLoadingDialog()
        val requestCall = serviceGradovi.getAll()
        requestCall.enqueue(object : Callback<List<Grad>> {
            override fun onFailure(call: Call<List<Grad>>, t: Throwable) {
                Toast.makeText(this@DonatorLicniPodaciAzurirajActivity,"Server error", Toast.LENGTH_SHORT).show()
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
        gradovi!!.add(0, Grad(-1,"Mjesto prebivalista"))
        var adapter = object : ArrayAdapter<Grad>(this@DonatorLicniPodaciAzurirajActivity,R.layout.spinner_list_item,gradovi!!){
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
        var index: Int = getIndex(spinnerMjestoPrebivalista!!, donator!!.mjestoPrebivalista)
        spinnerMjestoPrebivalista!!.setSelection(index)
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