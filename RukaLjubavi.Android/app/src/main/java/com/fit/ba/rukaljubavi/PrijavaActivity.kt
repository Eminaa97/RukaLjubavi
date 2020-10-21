package com.fit.ba.rukaljubavi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.fit.ba.rukaljubavi.Requests.BenefiktorInsertRequest
import com.fit.ba.rukaljubavi.Requests.PrijavaRequest
import com.fit.ba.rukaljubavi.Services.APIService
import com.fit.ba.rukaljubavi.Services.DonatorService
import com.fit.ba.rukaljubavi.Services.PrijavaService
import kotlinx.android.synthetic.main.activity_prijava.*
import kotlinx.android.synthetic.main.activity_registracija_benefiktor.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PrijavaActivity : AppCompatActivity() {

    private val service = APIService.buildService(PrijavaService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prijava)

        btnNewRegister.setOnClickListener {
            val intent = Intent(this,RegistracijaIzborActivity::class.java)
            startActivity(intent)
        }

        btnPrijava.setOnClickListener {
            login()
        }

    }

    private fun login() {
        var login = PrijavaRequest()
        login.email = txtPrUsername!!.text.toString()
        login.password = txtPrPassword!!.text.toString()

        var error: Boolean = false

        if (login.email.isNullOrBlank()) {
            txtPrUsername.setBackgroundResource(R.drawable.input_field_error)
            error = true
        }
        else
            txtPrUsername.setBackgroundResource(R.drawable.input_field)
        if (login.password.isNullOrBlank()) {
            txtPrPassword.setBackgroundResource(R.drawable.input_field_error)
            error = true
        }
        else
            txtPrPassword.setBackgroundResource(R.drawable.input_field)

        if (!error) {
            var loading = LoadingDialog(this@PrijavaActivity)
            loading.startLoadingDialog()

            val requestCall = service.login(login)
            requestCall.enqueue(object : Callback<Unit> {
                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    loading.stopDialog()
                    Toast.makeText(this@PrijavaActivity, "Server error", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    loading.stopDialog()
                    if (response.isSuccessful) {
                        val intent = Intent(this@PrijavaActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@PrijavaActivity,
                            "Pogrešno korisničko ime ili lozinka. Pokušajte ponovo.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
        }
    }
}