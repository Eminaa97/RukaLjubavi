package com.fit.ba.rukaljubavi

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.fit.ba.rukaljubavi.Models.Grad
import com.fit.ba.rukaljubavi.Requests.BenefiktorInsertRequest
import com.fit.ba.rukaljubavi.Services.APIService
import com.fit.ba.rukaljubavi.Services.BenefiktorService
import com.fit.ba.rukaljubavi.Services.GradService
import kotlinx.android.synthetic.main.activity_registracija_benefiktor.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegistracijaBenefiktorActivity : AppCompatActivity() {

    private val serviceGradovi = APIService.buildService(GradService::class.java)
    private val service = APIService.buildService(BenefiktorService::class.java)
    var benefiktor = BenefiktorInsertRequest()
    var gradovi: MutableList<Grad>? = arrayListOf()
    var spinner: Spinner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registracija_benefiktor)

        btnBack4.setOnClickListener {
            finish()
        }

        btnRegistracijaBenefiktorDalje.setOnClickListener {
            sendNoviBenefiktor()
        }

        spinner = spnBenLokacija
        loadGradovi()
        spinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var grad = p0!!.getItemAtPosition(p2) as Grad
                benefiktor.mjestoPrebivalistaId = grad.id
            }
        }
    }

    private fun loadGradovi() {
        var loading = LoadingDialog(this@RegistracijaBenefiktorActivity)
        loading.startLoadingDialog()
        val requestCall = serviceGradovi.getAll()
        requestCall.enqueue(object : Callback<List<Grad>> {
            override fun onFailure(call: Call<List<Grad>>, t: Throwable) {
                Toast.makeText(this@RegistracijaBenefiktorActivity,"Server error", Toast.LENGTH_SHORT).show()
                loading.stopDialog()
            }

            override fun onResponse(call: Call<List<Grad>>, response: Response<List<Grad>>) {
                if(response.isSuccessful){
                    var list = response.body()
                    gradovi = list!!.toMutableList()
                    gradovi!!.add(0,Grad(-1,"Lokacija"))
                    var adapter = object : ArrayAdapter<Grad>(this@RegistracijaBenefiktorActivity,R.layout.spinner_list_item,gradovi!!){
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

    private fun sendNoviBenefiktor() {
        benefiktor.nazivKompanije = txtRegNazivKompanije!!.text.toString()
        benefiktor.adresa = txtRegAdresa!!.text.toString()
        benefiktor.email = txtRegEmail!!.text.toString()
        benefiktor.password = txtRegPassword!!.text.toString()
        benefiktor.confirmPassword = txtRegPasswordPotvrda!!.text.toString()
        benefiktor.telefon = txtRegTelefon!!.text.toString()

        var error: Boolean = false

        if(benefiktor.nazivKompanije.isNullOrBlank()){
            txtRegNazivKompanije.setBackgroundResource(R.drawable.input_field_error)
            error = true
        }
        else
            txtRegNazivKompanije.setBackgroundResource(R.drawable.input_field)
        if(benefiktor.adresa.isNullOrBlank()){
            txtRegAdresa.setBackgroundResource(R.drawable.input_field_error)
            error = true
        }
        else
            txtRegAdresa.setBackgroundResource(R.drawable.input_field)
        if(benefiktor.email.isNullOrBlank()){
            txtRegEmail.setBackgroundResource(R.drawable.input_field_error)
            error = true
        }
        else{
            if(!TextUtils.isEmpty(benefiktor.email) && android.util.Patterns.EMAIL_ADDRESS.matcher(benefiktor.email).matches()){
                txtRegEmail.setBackgroundResource(R.drawable.input_field)
            }
            else{
                error = true
                txtRegEmail.setBackgroundResource(R.drawable.input_field_error)
            }
        }
        if(benefiktor.telefon.isNullOrBlank()){
            txtRegTelefon.setBackgroundResource(R.drawable.input_field_error)
            error = true
        }
        else
            txtRegTelefon.setBackgroundResource(R.drawable.input_field)
        if(benefiktor.password.isNullOrBlank()){
            txtRegPassword.setBackgroundResource(R.drawable.input_field_error)
            error = true
        }
        else{
            if(benefiktor.password!!.length >= 5){
                txtRegPassword.setBackgroundResource(R.drawable.input_field)
            }
            else{
                error = true
                txtRegPassword.setBackgroundResource(R.drawable.input_field_error)
                txtRegPassword.hint = "Minimalno 5 karaktera."
            }
        }
        if(benefiktor.confirmPassword.isNullOrBlank()){
            txtRegPasswordPotvrda.setBackgroundResource(R.drawable.input_field_error)
            error = true
        }
        else{
            if(benefiktor.password!!.length >= 5 && benefiktor.confirmPassword!! == benefiktor.password){
                txtRegPasswordPotvrda.setBackgroundResource(R.drawable.input_field)
            }
            else{
                error = true
                txtRegPasswordPotvrda.setBackgroundResource(R.drawable.input_field_error)
                txtRegPasswordPotvrda.hint = "Lozinke se ne slažu."
            }
        }
        if(benefiktor.mjestoPrebivalistaId == -1){
            spnBenLokacija.setBackgroundResource(R.drawable.spiner_field_error)
            error = true
        }
        else
            spnBenLokacija.setBackgroundResource(R.drawable.spiner_field)

        if(!error) {
            val intent = Intent(this, BenefiktorKategorijeActivity::class.java)
            intent.putExtra("NEW_BENEFIKTOR", benefiktor)
            startActivity(intent)
        }
    }
}