package com.fit.ba.rukaljubavi

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.fit.ba.rukaljubavi.Models.Donacija
import kotlinx.android.synthetic.main.activity_donacija_detalji.*
import kotlinx.android.synthetic.main.activity_donator_profil.*

class DonacijaDetaljiActivity : AppCompatActivity() {

    var donacije: Donacija? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donacija_detalji)
        title = "Detalji donacije"
        donacije = intent.getSerializableExtra("DONACIJA") as Donacija

        if(donacije!!.donatorId != 0 && donacije!!.benefiktorId != 0){
            btnDetaljiDoniraj.visibility = View.GONE;
        }
        else{
            btnDetaljiDoniraj.setOnClickListener {

            }
        }

        if(donacije!!.donatorId != 0){
            txtDonator.setText("""${donacije!!.donatorIme} ${donacije!!.donatorPrezime}""")
        }
        else{
            txtDonator.setText("")
        }
        if(donacije!!.benefiktorId != 0){
            txtBenefiktor.setText(donacije!!.benefiktorNazivKompanije)
        }
        else{
            txtBenefiktor.setText("")
        }

        txtKategorija.setText(donacije!!.nazivKategorije)
        txtStatus.setText(donacije!!.status)
        txtOpis.setText(donacije!!.opis)
        var dan = donacije!!.datumVrijeme.substring(8,10)
        var mjesec = donacije!!.datumVrijeme.substring(5,7)
        var godina = donacije!!.datumVrijeme.substring(0,4)
        txtDatum.text = "$dan.$mjesec.$godina"
    }
}