package com.fit.ba.rukaljubavi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_registracija_izbor.*

class RegistracijaIzborActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registracija_izbor)

        btnBack.setOnClickListener{
            finish()
        }

        btnRegistracijaDonator.setOnClickListener {
            val intent = Intent(this,RegistracijaDonatorActivity::class.java)
            intent.putExtra("ACTIVITY","RegistracijaIzborActivity")
            startActivity(intent)
        }

        btnRegistracijaBenefiktor.setOnClickListener {
            val intent = Intent(this,RegistracijaBenefiktorActivity::class.java)
            intent.putExtra("ACTIVITY","RegistracijaIzborActivity")
            startActivity(intent)
        }
    }
}