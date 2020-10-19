package com.fit.ba.rukaljubavi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.fit.ba.rukaljubavi.Requests.BenefiktorInsertRequest
import com.fit.ba.rukaljubavi.Services.APIService
import com.fit.ba.rukaljubavi.Services.BenefiktorService
import com.fit.ba.rukaljubavi.Services.DonatorService
import kotlinx.android.synthetic.main.activity_registracija_benefiktor.*
import kotlinx.android.synthetic.main.activity_registracija_benefiktor.btnBackToLogin
import kotlinx.android.synthetic.main.activity_registracija_benefiktor.btnSignUp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistracijaBenefiktorActivity : AppCompatActivity() {

    private val service = APIService.buildService(BenefiktorService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registracija_benefiktor)

        btnBackToLogin.setOnClickListener {
            val intent = Intent(this,PrijavaActivity::class.java)
            startActivity(intent)
        }

        btnSignUp.setOnClickListener {
            sendNoviBenefiktor()
        }
    }

    private fun sendNoviBenefiktor() {
        var benefiktor = BenefiktorInsertRequest()
        benefiktor.nazivKompanije = txtRegNazivKompanije!!.text.toString()
        benefiktor.pdvbroj = txtRegPDVbroj!!.text.toString()
        benefiktor.adresa = txtRegAdresa!!.text.toString()
        benefiktor.email = txtRegEmail!!.text.toString()
        benefiktor.password = txtRegPassword!!.text.toString()
        benefiktor.confirmPassword = txtRegPasswordPotvrda!!.text.toString()
        benefiktor.telefon = txtRegTelefon!!.text.toString()
        benefiktor.mjestoPrebivalistaId = txtregMjestoPrebivalista!!.text.toString().toIntOrNull()
        benefiktor.kategorije.add(0,1)

        val requestCall = service.send(benefiktor)
        requestCall.enqueue(object : Callback<Unit> {
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Toast.makeText(this@RegistracijaBenefiktorActivity,"Error: ${t.toString()}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(response.isSuccessful){
                    val intent = Intent(this@RegistracijaBenefiktorActivity,PrijavaActivity::class.java)
                    startActivity(intent)
                }
            }
        })
    }
}