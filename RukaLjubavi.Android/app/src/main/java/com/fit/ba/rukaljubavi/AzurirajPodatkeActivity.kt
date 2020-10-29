package com.fit.ba.rukaljubavi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fit.ba.rukaljubavi.Models.Benefiktor
import com.fit.ba.rukaljubavi.Models.Donator
import kotlinx.android.synthetic.main.activity_azuriraj_podatke.*

class AzurirajPodatkeActivity : AppCompatActivity() {

    var donator: Donator? = null
    var benefiktor: Benefiktor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_azuriraj_podatke)

        var previousActivity = intent.getStringExtra("ACTIVITY")
        if(previousActivity.equals("DonatorProfilActivity")){
            donator = intent.getSerializableExtra("DONATOR") as Donator
        }
        else{
            benefiktor = intent.getSerializableExtra("BENEFIKTOR") as Benefiktor
        }

        btnUpdateLicniPodaci.setOnClickListener {
            if(previousActivity.equals("DonatorProfilActivity")){
                val intent = Intent(this,DonatorLicniPodaciAzurirajActivity::class.java)
                intent.putExtra("ACTIVITY","AzurirajPodatkeActivity")
                intent.putExtra("DONATOR", donator)
                startActivity(intent)
            }
            else{
                val intent = Intent(this,BenefiktorLicniPodaciAzurirajActivity::class.java)
                intent.putExtra("ACTIVITY","AzurirajPodatkeActivity")
                intent.putExtra("BENEFIKTOR", benefiktor)
                startActivity(intent)
            }
        }

        btnBack5.setOnClickListener {
            if(previousActivity.equals("DonatorProfilActivity")){
                val intent = Intent(this,DonatorProfilActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                val intent = Intent(this,BenefiktorProfilActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        btnUpdateLozinka.setOnClickListener {
            val intent = Intent(this,LozinkaAzurirajActivity::class.java)
            if(previousActivity.equals("DonatorProfilActivity")){
                intent.putExtra("KORISNIK_ID", donator!!.korisnikId)
            }
            else{
                intent.putExtra("KORISNIK_ID", benefiktor!!.korisnikId)
            }
            startActivity(intent)
        }

        btnUpdateKategorije.setOnClickListener {
            val intent = Intent(this,AzurirajKategorijeActivity::class.java)
            if(previousActivity.equals("DonatorProfilActivity")){
                intent.putExtra("KORISNIK_ID", donator!!.korisnikId)
            }
            else{
                intent.putExtra("KORISNIK_ID", benefiktor!!.korisnikId)
            }
            startActivity(intent)
        }
    }
}