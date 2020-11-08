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
import java.util.*

lateinit var myAdapterZahtjeviVaseDonacije: VaseDonacijeRecyclerAdapter

class VaseDonacijeActivity : AppCompatActivity(), OnItemClickListener {

    var previousActivity: String? = null
    var dialog: AlertDialog? = null
    var kategorije: MutableList<DonacijaStatus>? = arrayListOf()
    var spinner: Spinner? = null
    var status: StatusDonacije? = null

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
                var s = p0!!.getItemAtPosition(p2) as DonacijaStatus
                status = s.status
                if(s.status == null)
                    status = null
                load()
            }
        }
    }

    class DonacijaStatus(val index: Int?,val status: StatusDonacije?, val text: String){
        override fun toString(): String {
            return text
        }
    }

    private fun loadKategorije() {
        kategorije!!.add(0, DonacijaStatus(0,null,"Status"))
        kategorije!!.add(1, DonacijaStatus(1,StatusDonacije.Aktivna,"Aktivna"))
        kategorije!!.add(2, DonacijaStatus(2,StatusDonacije.Na_cekanju,"Na_cekanju"))
        kategorije!!.add(3, DonacijaStatus(3,StatusDonacije.Prihvacena,"Prihvacena"))
        kategorije!!.add(4, DonacijaStatus(4,StatusDonacije.Odbijena,"Odbijena"))
        kategorije!!.add(5, DonacijaStatus(5,StatusDonacije.U_toku,"U_toku"))
        kategorije!!.add(6, DonacijaStatus(6,StatusDonacije.Zavrsena,"Zavrsena"))

        var adapter = ArrayAdapter<DonacijaStatus>(this@VaseDonacijeActivity,R.layout.layout_spinner_item,kategorije!!)
        adapter.setDropDownViewResource(R.layout.spinner_item)
        spinner!!.adapter = adapter
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
            serviceDonacije.get(DonatorId = APIService.loggedUserId, StatusDonacije = status)
        }
        else{
            serviceDonacije.get(BenefiktorId = APIService.loggedUserId, StatusDonacije = status)
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