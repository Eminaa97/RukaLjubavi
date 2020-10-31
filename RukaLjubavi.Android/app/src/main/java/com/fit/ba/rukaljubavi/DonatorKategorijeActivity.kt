package com.fit.ba.rukaljubavi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.fit.ba.rukaljubavi.Models.Kategorija
import com.fit.ba.rukaljubavi.Requests.BenefiktorInsertRequest
import com.fit.ba.rukaljubavi.Requests.DonatorInsertRequest
import com.fit.ba.rukaljubavi.Services.APIService
import com.fit.ba.rukaljubavi.Services.DonatorService
import com.fit.ba.rukaljubavi.Services.KategorijaService
import kotlinx.android.synthetic.main.activity_benefiktor_kategorije.*
import kotlinx.android.synthetic.main.activity_donator_kategorije.*
import kotlinx.android.synthetic.main.activity_donator_kategorije.btnBack3
import kotlinx.android.synthetic.main.activity_donator_kategorije.lstKategorije
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DonatorKategorijeActivity : AppCompatActivity(){

    private val service = APIService.buildService(KategorijaService::class.java)
    private val serviceDonator = APIService.buildService(DonatorService::class.java)
    var kategorije: MutableList<Kategorija>? = arrayListOf()
    var donator: DonatorInsertRequest? = null
    var lv: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donator_kategorije)

        donator = intent.getSerializableExtra("NEW_DONATOR") as DonatorInsertRequest

        btnBack3.setOnClickListener {
            finish()
        }

        btnRegistracijaDonatorZavrsi.setOnClickListener {
            if(donator!!.kategorije.size == 0){
                Toast.makeText(this@DonatorKategorijeActivity,"Niste izabrali nijednu kategoriju.", Toast.LENGTH_SHORT).show()
            }
            else{
                sendDonator(donator!!)
            }
        }

        lv = lstKategorije
        lv!!.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        loadKategorije()
        lv!!.setOnItemClickListener(object: AdapterView.OnItemClickListener{
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var selectedItem = parent!!.getItemAtPosition(position) as Kategorija
                if(donator!!.kategorije.contains(selectedItem.id))
                    donator!!.kategorije.remove(selectedItem.id)
                else
                    donator!!.kategorije.add(selectedItem.id)
            }
        })
    }

    private fun loadKategorije() {
        var loading = LoadingDialog(this@DonatorKategorijeActivity)
        loading.startLoadingDialog()
        val requestCall = service.getAll()
        requestCall.enqueue(object : Callback<List<Kategorija>> {
            override fun onFailure(call: Call<List<Kategorija>>, t: Throwable) {
                Toast.makeText(this@DonatorKategorijeActivity,"Server error", Toast.LENGTH_SHORT).show()
                loading.stopDialog()
            }

            override fun onResponse(call: Call<List<Kategorija>>, response: Response<List<Kategorija>>) {
                if(response.isSuccessful){
                    var list = response.body()
                    kategorije = list!!.toMutableList()
                    var adapter = ArrayAdapter<Kategorija>(this@DonatorKategorijeActivity,R.layout.row_layout,kategorije!!)
                    lv!!.adapter = adapter
                }
                loading.stopDialog()
            }
        })
    }

    private fun sendDonator(donator: DonatorInsertRequest) {
        val requestCall = serviceDonator.send(donator)

        var loading = LoadingDialog(this@DonatorKategorijeActivity)
        loading.startLoadingDialog()

        requestCall.enqueue(object : Callback<Unit> {
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                loading.stopDialog()
                Toast.makeText(this@DonatorKategorijeActivity,"Error: ${t.toString()}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(response.isSuccessful){
                    Toast.makeText(this@DonatorKategorijeActivity,"Uspješna registracija.", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@DonatorKategorijeActivity,PrijavaActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this@DonatorKategorijeActivity,response.message(), Toast.LENGTH_SHORT).show()
                }
                loading.stopDialog()
            }
        })
    }
}