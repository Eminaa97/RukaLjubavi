package com.fit.ba.rukaljubavi

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.fit.ba.rukaljubavi.Models.LogiraniUser
import com.fit.ba.rukaljubavi.Requests.PrijavaRequest
import com.fit.ba.rukaljubavi.Services.APIService
import com.fit.ba.rukaljubavi.Services.PrijavaService
import kotlinx.android.synthetic.main.activity_prijava.*
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
            requestCall.enqueue(object : Callback<LogiraniUser> {
                override fun onFailure(call: Call<LogiraniUser>, t: Throwable) {
                    loading.stopDialog()
                    Toast.makeText(this@PrijavaActivity, "Server error", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<LogiraniUser>, response: Response<LogiraniUser>) {
                    if (response.isSuccessful) {
                        var editor: SharedPreferences.Editor = sharedPreferences!!.edit()
                        val item = response.body()
                        if(item!!.tipKorisnika == 1){
                            val intent = Intent(this@PrijavaActivity, DonatorHomePageActivity::class.java)
                            startActivity(intent)
                            finish()
                            APIService.loggedUserId = item.donatorId
                            APIService.naziv = """${item.ime} ${item.prezime}"""
                        }
                        if(item!!.tipKorisnika == 2){
                            val intent = Intent(this@PrijavaActivity, BenefiktorHomePageActivity::class.java)
                            startActivity(intent)
                            finish()
                            APIService.loggedUserId = item.benefiktorId
                            APIService.naziv = item.nazivKompanije
                        }
                        editor.putString(Email, login.email)
                        editor.putString(Password, login.password)
                        editor.apply()
                        APIService.loggedUserType = item.tipKorisnika
                        APIService.loggedId = item.id
                        APIService.loggedUserToken = "Bearer " + item.token
                    } else {
                        Toast.makeText(
                            this@PrijavaActivity,
                            "Pogrešno korisničko ime ili lozinka. Pokušajte ponovo.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    loading.stopDialog()
                }
            })
        }
    }
}