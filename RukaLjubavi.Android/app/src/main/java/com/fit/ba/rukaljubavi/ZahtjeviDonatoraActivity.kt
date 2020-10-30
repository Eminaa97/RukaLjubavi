package com.fit.ba.rukaljubavi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.fit.ba.rukaljubavi.Helper.OnItemClickListener
import com.fit.ba.rukaljubavi.Helper.TopSpancingItemDecoration
import com.fit.ba.rukaljubavi.Models.Donacija
import com.fit.ba.rukaljubavi.Models.StatusDonacije
import kotlinx.android.synthetic.main.activity_aktivne_donacije.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

lateinit var myAdapterZahtjeviDonatora: ZahtjeviDonatoraRecyclerAdapter

class ZahtjeviDonatoraActivity : AppCompatActivity(), OnItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zahtjevi_donatora)
        initRecyclerView()
        load()
        title = "Zahtjevi donatora"
    }

    private fun initRecyclerView(){
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@ZahtjeviDonatoraActivity)
            val topSpacingDecoration =
                TopSpancingItemDecoration(30)
            addItemDecoration(topSpacingDecoration)
            myAdapterZahtjeviDonatora = ZahtjeviDonatoraRecyclerAdapter(this@ZahtjeviDonatoraActivity)
            adapter = myAdapterZahtjeviDonatora
        }
    }

    private fun load(){
        var loading = LoadingDialog(this@ZahtjeviDonatoraActivity)
        loading.startLoadingDialog()

        val requestCall = serviceDonacije.get(StatusDonacije = StatusDonacije.Na_cekanju)
        requestCall.enqueue(object : Callback<List<Donacija>> {
            override fun onFailure(call: Call<List<Donacija>>, t: Throwable) {
                Toast.makeText(this@ZahtjeviDonatoraActivity,"Error: ${t.toString()}", Toast.LENGTH_SHORT).show()
                loading.stopDialog()
            }

            override fun onResponse(call: Call<List<Donacija>>, response: Response<List<Donacija>>) {
                if(response.isSuccessful){
                    val list = response.body()!!
                    myAdapterZahtjeviDonatora.submitList(list)
                    myAdapterZahtjeviDonatora.notifyDataSetChanged()
                }
                else{
                    Toast.makeText(this@ZahtjeviDonatoraActivity,"Server error", Toast.LENGTH_SHORT).show()
                }
                loading.stopDialog()
            }
        })
    }

    override fun <T> onItemClick(item: T, position: Int) {
        val intent = Intent(this, DonacijaDetaljiActivity::class.java)
        intent.putExtra("DONACIJA",item as Donacija)
        intent.putExtra("ACTIVITY","ZahtjeviDonatoraActivity")
        startActivity(intent)
    }
}