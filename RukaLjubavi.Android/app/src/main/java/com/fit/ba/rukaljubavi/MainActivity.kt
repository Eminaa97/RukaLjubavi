package com.fit.ba.rukaljubavi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.fit.ba.rukaljubavi.Models.Drzava
import com.fit.ba.rukaljubavi.R
import com.fit.ba.rukaljubavi.Services.APIService
import com.fit.ba.rukaljubavi.Services.DrzavaService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity()
{
    private val service = APIService.buildService(DrzavaService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAbout.setOnClickListener {
            val intent = Intent(this,AboutActivity::class.java)
            startActivity(intent)
            load()
        }

        btnLogout.setOnClickListener {
            val intent = Intent(this,PrijavaActivity::class.java)
            startActivity(intent)
            load()
        }
    }

    private fun load(){
        val requestCall = service.getAll()
        requestCall.enqueue(object : Callback<List<Drzava>> {
            override fun onFailure(call: Call<List<Drzava>>, t: Throwable) {
                Toast.makeText(this@MainActivity,"Error: ${t.toString()}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Drzava>>, response: Response<List<Drzava>>) {
                if(response.isSuccessful){
                    val list = response.body()!!
                    Toast.makeText(this@MainActivity,list[0].naziv, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}