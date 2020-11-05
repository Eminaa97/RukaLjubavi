package com.fit.ba.rukaljubavi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.fit.ba.rukaljubavi.Requests.BenefiktorInsertRequest
import com.fit.ba.rukaljubavi.Services.APIService
import com.fit.ba.rukaljubavi.Services.BenefiktorService
import kotlinx.android.synthetic.main.activity_p_d_v_broj_potvrda.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PDVBrojPotvrdaActivity : AppCompatActivity() {

    private val service = APIService.buildService(BenefiktorService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_p_d_v_broj_potvrda)

        var benefiktor = intent.getSerializableExtra("NEW_BENEFIKTOR") as BenefiktorInsertRequest

        btnBack2.setOnClickListener {
            finish()
        }

        btnProvjeriPdvBroj.setOnClickListener {
            benefiktor.pdvbroj = txtRegPDVBroj!!.text.toString()
            if(benefiktor.pdvbroj.isNullOrBlank()){
                txtRegPDVBroj.setBackgroundResource(R.drawable.input_field_error)
            }
            else
                sendBenefiktor(benefiktor)
        }
    }

    private fun sendBenefiktor(benefiktor: BenefiktorInsertRequest) {
        val requestCall = service.send(benefiktor)

        var loading = LoadingDialog(this@PDVBrojPotvrdaActivity)
        loading.startLoadingDialog()

        requestCall.enqueue(object : Callback<Unit> {
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                loading.stopDialog()
                Toast.makeText(this@PDVBrojPotvrdaActivity,"Error: ${t.toString()}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(response.isSuccessful){
                    Toast.makeText(this@PDVBrojPotvrdaActivity,"Uspješna registracija.", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@PDVBrojPotvrdaActivity,PrijavaActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this@PDVBrojPotvrdaActivity,"Nepostojeći PDV broj.", Toast.LENGTH_SHORT).show()
                }
                loading.stopDialog()
            }
        })
    }
}