package com.fit.ba.rukaljubavi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fit.ba.rukaljubavi.Services.TOPIC
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_donator_home_page.*

class DonatorHomePageActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donator_home_page)

        FirebaseMessaging.getInstance().subscribeToTopic("donator")

        btnAbout.setOnClickListener {
            val intent = Intent(this,AboutActivity::class.java)
            startActivity(intent)
        }

        btnBenefiktori.setOnClickListener {
            val intent = Intent(this,BenefiktoriListaActivity::class.java)
            intent.putExtra("ACTIVITY","DonatorHomePageActivity")
            startActivity(intent)
        }

        btnLogout.setOnClickListener {
            OdjavaAlertDialog(this@DonatorHomePageActivity).startAlertDialog(PrijavaActivity::class.java)
            FirebaseMessaging.getInstance().unsubscribeFromTopic("donator")
        }

        btnMainProfile.setOnClickListener {
            val intent = Intent(this,DonatorProfilActivity::class.java)
            startActivity(intent)
        }

        btnNovaDonacija.setOnClickListener {
            val intent = Intent(this,NovaDonacijaActivity::class.java)
            intent.putExtra("ACTIVITY","DonatorHomePageActivity")
            startActivity(intent)
        }


        btnDonacije.setOnClickListener {
            val intent = Intent(this,ZahtjeviBenefiktoraListaActivity::class.java)
            intent.putExtra("ACTIVITY","DonatorHomePageActivity")
            startActivity(intent)
        }

        btnVaseDonacije.setOnClickListener {
            val intent = Intent(this,VaseDonacijeActivity::class.java)
            intent.putExtra("ACTIVITY","DonatorHomePageActivity")
            startActivity(intent)
        }
    }
}