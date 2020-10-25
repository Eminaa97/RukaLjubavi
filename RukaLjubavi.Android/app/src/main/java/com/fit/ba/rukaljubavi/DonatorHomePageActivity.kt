package com.fit.ba.rukaljubavi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class DonatorHomePageActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAbout.setOnClickListener {
            val intent = Intent(this,AboutActivity::class.java)
            startActivity(intent)
        }

        btnBenefiktori.setOnClickListener {
            val intent = Intent(this,BenefiktoriListaActivity::class.java)
            startActivity(intent)
        }

        btnLogout.setOnClickListener {
            OdjavaAlertDialog(this@DonatorHomePageActivity).startAlertDialog(PrijavaActivity::class.java)
        }

        btnMainProfile.setOnClickListener {
            val intent = Intent(this,DonatorProfilActivity::class.java)
            startActivity(intent)
        }
    }
}