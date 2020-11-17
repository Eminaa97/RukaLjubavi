package com.fit.ba.rukaljubavi

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.fit.ba.rukaljubavi.Models.Notification
import com.fit.ba.rukaljubavi.Models.PushNotification
import com.fit.ba.rukaljubavi.Services.APIService
import com.fit.ba.rukaljubavi.Services.FirebaseRetrofitInstance
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_benefiktor_home_page.*
import kotlinx.android.synthetic.main.activity_benefiktor_home_page.btnAbout
import kotlinx.android.synthetic.main.activity_benefiktor_home_page.btnLogout
import kotlinx.android.synthetic.main.activity_benefiktor_home_page.btnMainProfile

class BenefiktorHomePageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_benefiktor_home_page)

        FirebaseMessaging.getInstance().subscribeToTopic(sharedPreferences!!.getString("TOPIC", "NAN")!!)

        btnAbout.setOnClickListener {
            val intent = Intent(this,AboutActivity::class.java)
            startActivity(intent)
        }

        btnLogout.setOnClickListener {
            OdjavaAlertDialog(this@BenefiktorHomePageActivity).startAlertDialog(PrijavaActivity::class.java)
            FirebaseMessaging.getInstance().unsubscribeFromTopic(sharedPreferences!!.getString("TOPIC", "NAN")!!)
        }

        btnMainProfile.setOnClickListener {
            val intent = Intent(this,BenefiktorProfilActivity::class.java)
            intent.putExtra("ACTIVITY","BenefiktorHomePageActivity")
            startActivity(intent)
        }

        btnNoviZahtjevZaDonacijom.setOnClickListener {
            val intent = Intent(this,NoviZahtjevZaDonacijomActivity::class.java)
            intent.putExtra("ACTIVITY","BenefiktorHomePageActivity")
            startActivity(intent)
        }

        btnVaseDonacije.setOnClickListener {
            val intent = Intent(this,VaseDonacijeActivity::class.java)
            intent.putExtra("ACTIVITY","BenefiktorHomePageActivity")
            startActivity(intent)
        }

        btnAktivneDonacije.setOnClickListener {
            val intent = Intent(this,AktivneDonacijeActivity::class.java)
            intent.putExtra("ACTIVITY","BenefiktorHomePageActivity")
            startActivity(intent)
        }

        btnZahtjevi.setOnClickListener {
            val intent = Intent(this,ZahtjeviDonatoraActivity::class.java)
            intent.putExtra("ACTIVITY","BenefiktorHomePageActivity")
            startActivity(intent)
        }
    }
}