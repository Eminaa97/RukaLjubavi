package com.fit.ba.rukaljubavi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fit.ba.rukaljubavi.Models.Donacija

class ZahtjeviBenefiktoraDetaljiActivity : AppCompatActivity() {

    var donacija: Donacija? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zahtjevi_benefiktora_detalji)

        donacija = intent.getSerializableExtra("DONACIJA") as Donacija
    }
}