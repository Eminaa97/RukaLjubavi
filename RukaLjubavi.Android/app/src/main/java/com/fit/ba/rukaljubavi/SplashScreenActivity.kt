package com.fit.ba.rukaljubavi

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.view.WindowCompat
import com.fit.ba.rukaljubavi.Models.LogiraniUser
import com.fit.ba.rukaljubavi.Requests.PrijavaRequest
import com.fit.ba.rukaljubavi.Services.APIService
import com.fit.ba.rukaljubavi.Services.PrijavaService
import kotlinx.android.synthetic.main.activity_splash_screen.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

var sharedPreferences: SharedPreferences? = null
var Email: String = "username"
var Password: String = "password"

class SplashScreenActivity : AppCompatActivity() {

    var fileName: String = "loginData"
    private val service = APIService.buildService(PrijavaService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        sharedPreferences = getSharedPreferences(fileName, Context.MODE_PRIVATE)

        if (sharedPreferences!!.contains(Email)) {
            var login = PrijavaRequest()
            login.email = sharedPreferences!!.getString(Email, "NAN")
            login.password = sharedPreferences!!.getString(Password, "NAN")
            val requestCall = service.login(login)
            requestCall.enqueue(object : Callback<LogiraniUser> {
                override fun onFailure(call: Call<LogiraniUser>, t: Throwable) {
                }

                override fun onResponse(call: Call<LogiraniUser>, response: Response<LogiraniUser>) {
                    if (response.isSuccessful) {
                        val item = response.body()
                        if (item!!.tipKorisnika == 1) {
                            val intent = Intent(this@SplashScreenActivity, DonatorHomePageActivity::class.java)
                            startActivity(intent)
                            finish()
                            APIService.loggedUserId = item.donatorId
                            APIService.naziv = """${item.ime} ${item.prezime}"""
                        }
                        if (item!!.tipKorisnika == 2) {
                            val intent = Intent(this@SplashScreenActivity, BenefiktorHomePageActivity::class.java)
                            startActivity(intent)
                            finish()
                            APIService.loggedUserId = item.benefiktorId
                            APIService.naziv = item.nazivKompanije
                        }
                        APIService.loggedUserType = item.tipKorisnika
                        APIService.loggedId = item.id
                        APIService.loggedUserToken = "Bearer " + item.token
                    }
                }
            })
        } else {
            @Suppress("DEPRECATION")
            Handler().postDelayed({
                sharedPreferences!!.edit().clear().apply()
                val intent = Intent(this, PrijavaActivity::class.java)
                startActivity(intent)
                finish()
            }, 2000)
        }
    }
}