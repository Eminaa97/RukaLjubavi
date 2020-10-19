package com.fit.ba.rukaljubavi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.fit.ba.rukaljubavi.Models.Drzava
import com.fit.ba.rukaljubavi.Requests.DonatorInsertRequest
import com.fit.ba.rukaljubavi.Services.APIService
import com.fit.ba.rukaljubavi.Services.DonatorService
import kotlinx.android.synthetic.main.activity_registracija_donator.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistracijaDonatorActivity : AppCompatActivity() {

    private val service = APIService.buildService(DonatorService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registracija_donator)

        btnBackToLogin.setOnClickListener {
            val intent = Intent(this,PrijavaActivity::class.java)
            startActivity(intent)
        }

        btnSignUp.setOnClickListener {
            sendNoviDonator()
        }
    }

    private fun sendNoviDonator() {
        var donator = DonatorInsertRequest()
        donator.ime = txtRegIme!!.text.toString()
        donator.prezime = txtRegPrezime!!.text.toString()
        donator.adresa = txtRegAdresa!!.text.toString()
        donator.email = txtRegEmail!!.text.toString()
        donator.jmbg = txtRegJMBG!!.text.toString()
        donator.password = txtRegPassword!!.text.toString()
        donator.confirmPassword = txtRegPasswordPotvrda!!.text.toString()
        donator.telefon = txtRegTelefon!!.text.toString()
        donator.mjestoPrebivalistaId = txtregMjestoPrebivalista!!.text.toString().toIntOrNull()
        donator.datumRodjenja = txtregDatumRodjenja!!.text.toString()
        donator.mjestoRodjenjaId = txtregMjestoRodjenja!!.text.toString().toIntOrNull()
        donator.kategorije.add(0,1)

        val requestCall = service.send(donator)
        requestCall.enqueue(object : Callback<Unit> {
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Toast.makeText(this@RegistracijaDonatorActivity,"Error: ${t.toString()}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(response.isSuccessful){
                    val intent = Intent(this@RegistracijaDonatorActivity,PrijavaActivity::class.java)
                    startActivity(intent)
                }
            }
        })
    }
}