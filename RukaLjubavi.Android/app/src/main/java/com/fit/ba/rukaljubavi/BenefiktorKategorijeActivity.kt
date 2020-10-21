package com.fit.ba.rukaljubavi

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import com.fit.ba.rukaljubavi.Models.Grad
import com.fit.ba.rukaljubavi.Models.Kategorija
import com.fit.ba.rukaljubavi.Requests.BenefiktorInsertRequest
import com.fit.ba.rukaljubavi.Services.APIService
import com.fit.ba.rukaljubavi.Services.BenefiktorService
import com.fit.ba.rukaljubavi.Services.KategorijaService
import kotlinx.android.synthetic.main.activity_benefiktor_kategorije.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BenefiktorKategorijeActivity : AppCompatActivity() {

    private val service = APIService.buildService(KategorijaService::class.java)
    var kategorije: MutableList<Kategorija>? = arrayListOf()
    var benefiktor: BenefiktorInsertRequest? = null
    var lv: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_benefiktor_kategorije)

        benefiktor = intent.getSerializableExtra("NEW_BENEFIKTOR") as BenefiktorInsertRequest

        btnBack3.setOnClickListener {
            val intent = Intent(this,RegistracijaBenefiktorActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnRegistracijaBenefiktorDalje2.setOnClickListener {
            val intent = Intent(this,PDVBrojPotvrdaActivity::class.java)
            intent.putExtra("NEW_BENEFIKTOR", benefiktor)
            startActivity(intent)
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
                if(benefiktor!!.kategorije.contains(selectedItem.id))
                    benefiktor!!.kategorije.remove(selectedItem.id)
                else
                    benefiktor!!.kategorije.add(selectedItem.id)
            }
        })
    }

    private fun loadKategorije() {
        var loading = LoadingDialog(this@BenefiktorKategorijeActivity)
        loading.startLoadingDialog()
        val requestCall = service.getAll()
        requestCall.enqueue(object : Callback<List<Kategorija>> {
            override fun onFailure(call: Call<List<Kategorija>>, t: Throwable) {
                Toast.makeText(this@BenefiktorKategorijeActivity,"Server error", Toast.LENGTH_SHORT).show()
                loading.stopDialog()
            }

            override fun onResponse(call: Call<List<Kategorija>>, response: Response<List<Kategorija>>) {
                if(response.isSuccessful){
                    var list = response.body()
                    kategorije = list!!.toMutableList()
                    var adapter = ArrayAdapter<Kategorija>(this@BenefiktorKategorijeActivity,R.layout.row_layout,kategorije!!)
                    lv!!.adapter = adapter
                }
                loading.stopDialog()
            }
        })
    }
}