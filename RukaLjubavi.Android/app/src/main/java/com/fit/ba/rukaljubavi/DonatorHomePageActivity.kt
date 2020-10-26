package com.fit.ba.rukaljubavi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_donator_home_page.*

class DonatorHomePageActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donator_home_page)

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
    }
}