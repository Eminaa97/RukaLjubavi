package com.fit.ba.rukaljubavi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.fit.ba.rukaljubavi.Requests.LozinkaUpdateRequest
import com.fit.ba.rukaljubavi.Services.APIService
import com.fit.ba.rukaljubavi.Services.BenefiktorService
import com.fit.ba.rukaljubavi.Services.PrijavaService
import kotlinx.android.synthetic.main.activity_benefiktor_licni_podaci_azuriraj.*
import kotlinx.android.synthetic.main.activity_lozinka_azuriraj.*
import kotlinx.android.synthetic.main.activity_lozinka_azuriraj.btnBack4
import kotlinx.android.synthetic.main.activity_registracija_donator.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LozinkaAzurirajActivity : AppCompatActivity() {

    private val service = APIService.buildService(PrijavaService::class.java)
    var userId: Int? = null
    var lozinkaRequest = LozinkaUpdateRequest()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lozinka_azuriraj)

        userId = intent.getIntExtra("KORISNIK_ID", 0)

        btnBack4.setOnClickListener {
            finish()
        }

        btnAzuriraj.setOnClickListener {
            updatePass()
        }
    }

    private fun updatePass() {
        lozinkaRequest.oldPassword = txtOldPass!!.text.toString()
        lozinkaRequest.newPassword = txtNewPass!!.text.toString()
        lozinkaRequest.newPasswordConfirm = txtConfirm!!.text.toString()

        var error: Boolean = false

        if(lozinkaRequest.oldPassword.isNullOrBlank()){
            txtOldPass.setBackgroundResource(R.drawable.input_field_error)
            error = true
        }
        else
            txtOldPass.setBackgroundResource(R.drawable.input_field)
        if(lozinkaRequest.newPassword.isNullOrBlank()){
            txtNewPass.setBackgroundResource(R.drawable.input_field_error)
            error = true
        }
        else{
            if(lozinkaRequest.newPassword!!.length >= 5){
                txtNewPass.setBackgroundResource(R.drawable.input_field)
            }
            else{
                error = true
                txtNewPass.setBackgroundResource(R.drawable.input_field_error)
                txtNewPass.hint = "Minimalno 5 karaktera."
            }
        }
        if(lozinkaRequest.newPasswordConfirm.isNullOrBlank()){
            txtConfirm.setBackgroundResource(R.drawable.input_field_error)
            error = true
        }
        else{
            if(lozinkaRequest.newPassword!!.length >= 5 && lozinkaRequest.newPasswordConfirm!! == lozinkaRequest.newPassword){
                txtConfirm.setBackgroundResource(R.drawable.input_field)
            }
            else{
                error = true
                txtConfirm.setBackgroundResource(R.drawable.input_field_error)
                txtConfirm.hint = "Lozinke se ne slažu."
            }
        }

        if(!error) {
            sendPass()
        }
    }

    private fun sendPass() {
        val requestCall = service.resetPassword(APIService.loggedUserToken ,lozinkaRequest)

        var loading = LoadingDialog(this@LozinkaAzurirajActivity)
        loading.startLoadingDialog()

        requestCall.enqueue(object : Callback<Unit> {
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                loading.stopDialog()
                Toast.makeText(this@LozinkaAzurirajActivity,"Error: ${t.toString()}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(response.isSuccessful){
                    Toast.makeText(this@LozinkaAzurirajActivity,"Uspješno ažuriranje.", Toast.LENGTH_SHORT).show()
                    finish()
                }
                else{
                    Toast.makeText(this@LozinkaAzurirajActivity,response.message(), Toast.LENGTH_SHORT).show()
                }
                loading.stopDialog()
            }
        })
    }
}