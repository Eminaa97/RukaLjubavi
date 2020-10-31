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
import com.fit.ba.rukaljubavi.Requests.KategorijeUpdateRequest
import com.fit.ba.rukaljubavi.Services.APIService
import com.fit.ba.rukaljubavi.Services.KategorijaService
import com.fit.ba.rukaljubavi.Services.PrijavaService
import kotlinx.android.synthetic.main.activity_azuriraj_kategorije.*
import kotlinx.android.synthetic.main.activity_benefiktor_kategorije.*
import kotlinx.android.synthetic.main.activity_benefiktor_kategorije.lstKategorije
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AzurirajKategorijeActivity : AppCompatActivity() {

    private val servicePrijava = APIService.buildService(PrijavaService::class.java)
    private val service = APIService.buildService(KategorijaService::class.java)
    var kategorije: MutableList<Kategorija>? = arrayListOf()
    var userId: Int? = null
    var lv: ListView? = null
    var newKategorije: KategorijeUpdateRequest = KategorijeUpdateRequest()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_azuriraj_kategorije)

        userId = intent.getIntExtra("KORISNIK_ID", 0)

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
                if(newKategorije!!.kategorije.contains(selectedItem.id))
                    newKategorije!!.kategorije.remove(selectedItem.id)
                else
                    newKategorije!!.kategorije.add(selectedItem.id)
            }
        })

        btnAzurirajKategorije.setOnClickListener {
            if(newKategorije!!.kategorije.size == 0){
                Toast.makeText(this@AzurirajKategorijeActivity,"Niste izabrali nijednu kategoriju.", Toast.LENGTH_SHORT).show()
            }
            else{
                sendNewKategorije()
            }
        }

        btnBack71.setOnClickListener {
            finish()
        }

    }

    private fun sendNewKategorije() {
        val requestCall = servicePrijava.updateKategorije(APIService.loggedUserToken ,newKategorije)

        var loading = LoadingDialog(this@AzurirajKategorijeActivity)
        loading.startLoadingDialog()

        requestCall.enqueue(object : Callback<Unit> {
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                loading.stopDialog()
                Toast.makeText(this@AzurirajKategorijeActivity,"Error: ${t.toString()}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(response.isSuccessful){
                    Toast.makeText(this@AzurirajKategorijeActivity,"Uspješno ažuriranje.", Toast.LENGTH_SHORT).show()
                    finish()
                }
                else{
                    Toast.makeText(this@AzurirajKategorijeActivity,response.message(), Toast.LENGTH_SHORT).show()
                }
                loading.stopDialog()
            }
        })
    }

    private fun loadKategorije() {
        var loading = LoadingDialog(this@AzurirajKategorijeActivity)
        loading.startLoadingDialog()
        val requestCall = service.getAll()
        requestCall.enqueue(object : Callback<List<Kategorija>> {
            override fun onFailure(call: Call<List<Kategorija>>, t: Throwable) {
                Toast.makeText(this@AzurirajKategorijeActivity,"Server error", Toast.LENGTH_SHORT).show()
                loading.stopDialog()
            }

            override fun onResponse(call: Call<List<Kategorija>>, response: Response<List<Kategorija>>) {
                if(response.isSuccessful){
                    var list = response.body()
                    kategorije = list!!.toMutableList()
                    var adapter = ArrayAdapter<Kategorija>(this@AzurirajKategorijeActivity,R.layout.row_layout,kategorije!!)
                    lv!!.adapter = adapter
                }
                loading.stopDialog()
            }
        })
    }
}