package com.fit.ba.rukaljubavi

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.fit.ba.rukaljubavi.Helper.OnItemClickListener
import com.fit.ba.rukaljubavi.Helper.TopSpancingItemDecoration
import com.fit.ba.rukaljubavi.Models.Donacija
import com.fit.ba.rukaljubavi.Models.Grad
import com.fit.ba.rukaljubavi.Models.Kategorija
import com.fit.ba.rukaljubavi.Services.APIService
import com.fit.ba.rukaljubavi.Models.StatusDonacije
import com.fit.ba.rukaljubavi.Services.GradService
import com.fit.ba.rukaljubavi.Services.KategorijaService
import kotlinx.android.synthetic.main.activity_vase_donacije.*
import kotlinx.android.synthetic.main.activity_zahtjevi_benefiktora_lista.recycler_view
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class VaseDonacijeActivity : AppCompatActivity(), OnItemClickListener {

    var previousActivity: String? = null
    lateinit var myAdapterZahtjeviVaseDonacije: VaseDonacijeRecyclerAdapter
    private val serviceKategorije = APIService.buildService(KategorijaService::class.java)
    var dialog: AlertDialog? = null
    var kategorije: MutableList<Kategorija>? = arrayListOf()
    var spinner: Spinner? = null
    var kategorijaId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vase_donacije)

        previousActivity = intent.getStringExtra("ACTIVITY")
        title  = "Vaše donacije"
        initRecyclerView()
        load()

        spinner = btnFilterVaseDonacije
        loadKategorije()
        spinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var k = p0!!.getItemAtPosition(p2) as Kategorija
                kategorijaId = k.id
                if(k.id == -1)
                    kategorijaId = null
                load()
            }
        }
    }

    private fun loadKategorije() {
        var loading = LoadingDialog(this@VaseDonacijeActivity)
        loading.startLoadingDialog()
        val requestCall = serviceKategorije.getAll()
        requestCall.enqueue(object : Callback<List<Kategorija>> {
            override fun onFailure(call: Call<List<Kategorija>>, t: Throwable) {
                Toast.makeText(this@VaseDonacijeActivity,"Server error", Toast.LENGTH_SHORT).show()
                loading.stopDialog()
            }

            override fun onResponse(call: Call<List<Kategorija>>, response: Response<List<Kategorija>>) {
                if(response.isSuccessful){
                    var list = response.body()
                    kategorije = list!!.toMutableList()
                    kategorije!!.add(0, Kategorija(-1,"Status"))
                    var adapter = ArrayAdapter<Kategorija>(this@VaseDonacijeActivity,R.layout.layout_spinner_item,kategorije!!)
                    adapter.setDropDownViewResource(R.layout.spinner_item)
                    spinner!!.adapter = adapter
                }
                loading.stopDialog()
            }
        })
    }

    private fun initRecyclerView(){
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@VaseDonacijeActivity)
            val topSpacingDecoration =
                TopSpancingItemDecoration(30)
            addItemDecoration(topSpacingDecoration)
            myAdapterZahtjeviVaseDonacije = VaseDonacijeRecyclerAdapter(this@VaseDonacijeActivity)
            adapter = myAdapterZahtjeviVaseDonacije
        }
    }

    private fun load(){
        var loading = LoadingDialog(this@VaseDonacijeActivity)
        loading.startLoadingDialog()

        val requestCall = if(previousActivity.equals("DonatorHomePageActivity")){
            serviceDonacije.get(DonatorId = APIService.loggedUserId, KategorijaId = kategorijaId)
        }
        else{
            serviceDonacije.get(BenefiktorId = APIService.loggedUserId, KategorijaId = kategorijaId)
        }

        requestCall.enqueue(object : Callback<List<Donacija>> {
            override fun onFailure(call: Call<List<Donacija>>, t: Throwable) {
                Toast.makeText(this@VaseDonacijeActivity,"Error: ${t.toString()}", Toast.LENGTH_SHORT).show()
                loading.stopDialog()
            }

            override fun onResponse(call: Call<List<Donacija>>, response: Response<List<Donacija>>) {
                if(response.isSuccessful){
                    val list = response.body()!!
                    if(list.isEmpty()){
                        Toast.makeText(this@VaseDonacijeActivity,"Nemate nijednu donaciju.", Toast.LENGTH_SHORT).show()
                    }
                    myAdapterZahtjeviVaseDonacije.submitList(list)
                    myAdapterZahtjeviVaseDonacije.notifyDataSetChanged()
                }
                else{
                    Toast.makeText(this@VaseDonacijeActivity,"Server error", Toast.LENGTH_SHORT).show()
                }
                loading.stopDialog()
            }
        })
    }

    override fun <T> onItemClick(item: T, position: Int) {
        val intent = Intent(this, DonacijaDetaljiActivity::class.java)
        intent.putExtra("DONACIJA",item as Donacija)
        intent.putExtra("ACTIVITY","VaseDonacijeActivity")
        startActivity(intent)
    }
}