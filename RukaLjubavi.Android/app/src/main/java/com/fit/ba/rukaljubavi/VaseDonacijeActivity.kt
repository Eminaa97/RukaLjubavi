package com.fit.ba.rukaljubavi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.fit.ba.rukaljubavi.Helper.OnItemClickListener
import com.fit.ba.rukaljubavi.Helper.TopSpancingItemDecoration
import com.fit.ba.rukaljubavi.Models.Benefiktor
import com.fit.ba.rukaljubavi.Models.Donacija
import com.fit.ba.rukaljubavi.Services.APIService
import com.fit.ba.rukaljubavi.Services.DonacijaService
import com.fit.ba.rukaljubavi.Services.StatusDonacije
import kotlinx.android.synthetic.main.activity_benefiktori_lista.*
import kotlinx.android.synthetic.main.activity_zahtjevi_benefiktora_lista.*
import kotlinx.android.synthetic.main.activity_zahtjevi_benefiktora_lista.recycler_view
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

lateinit var myAdapterZahtjeviVaseDonacije: VaseDonacijeRecyclerAdapter

class VaseDonacijeActivity : AppCompatActivity(), OnItemClickListener {

    var previousActivity: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vase_donacije)

        previousActivity = intent.getStringExtra("ACTIVITY")
        title  = "Vaše donacije"
        initRecyclerView()
        load()
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
            serviceDonacije.get(DonatorId = APIService.loggedUserId, StatusDonacije = StatusDonacije.Zavrsena)
        }
        else{
            serviceDonacije.get(BenefiktorId = APIService.loggedUserId, StatusDonacije = StatusDonacije.Zavrsena)
        }

        requestCall.enqueue(object : Callback<List<Donacija>> {
            override fun onFailure(call: Call<List<Donacija>>, t: Throwable) {
                Toast.makeText(this@VaseDonacijeActivity,"Error: ${t.toString()}", Toast.LENGTH_SHORT).show()
                loading.stopDialog()
            }

            override fun onResponse(call: Call<List<Donacija>>, response: Response<List<Donacija>>) {
                if(response.isSuccessful){
                    val list = response.body()!!
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