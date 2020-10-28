package com.fit.ba.rukaljubavi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.fit.ba.rukaljubavi.Helper.OnItemClickListener
import com.fit.ba.rukaljubavi.Helper.TopSpancingItemDecoration
import com.fit.ba.rukaljubavi.Models.Benefiktor
import com.fit.ba.rukaljubavi.Models.Donacija
import com.fit.ba.rukaljubavi.Services.APIService
import com.fit.ba.rukaljubavi.Services.BenefiktorService
import com.fit.ba.rukaljubavi.Services.DonacijaService
import kotlinx.android.synthetic.main.activity_benefiktori_lista.*
import kotlinx.android.synthetic.main.activity_benefiktori_lista.recycler_view
import kotlinx.android.synthetic.main.activity_zahtjevi_benefiktora_lista.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

lateinit var myAdapterZahtjeviBenefiktoraLista: ZahtjeviBenefiktoraListaRecyclerAdapter
val serviceDonacije = APIService.buildService(DonacijaService::class.java)

class ZahtjeviBenefiktoraListaActivity : AppCompatActivity(), OnItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zahtjevi_benefiktora_lista)

        title  = "Zahtjevi benefiktora"
        initRecyclerView()
        load()

        btnFilter2.setOnClickListener {
            ZahtjeviBenefiktoraFilterDialog(this@ZahtjeviBenefiktoraListaActivity).startDialog()
        }
    }

    private fun initRecyclerView(){
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@ZahtjeviBenefiktoraListaActivity)
            val topSpacingDecoration =
                TopSpancingItemDecoration(30)
            addItemDecoration(topSpacingDecoration)
            myAdapterZahtjeviBenefiktoraLista = ZahtjeviBenefiktoraListaRecyclerAdapter(this@ZahtjeviBenefiktoraListaActivity)
            adapter = myAdapterZahtjeviBenefiktoraLista
        }
    }

    private fun load(){
        var loading = LoadingDialog(this@ZahtjeviBenefiktoraListaActivity)
        loading.startLoadingDialog()

        val requestCall = serviceDonacije.get(true)
        requestCall.enqueue(object : Callback<List<Donacija>> {
            override fun onFailure(call: Call<List<Donacija>>, t: Throwable) {
                Toast.makeText(this@ZahtjeviBenefiktoraListaActivity,"Error: ${t.toString()}", Toast.LENGTH_SHORT).show()
                loading.stopDialog()
            }

            override fun onResponse(call: Call<List<Donacija>>, response: Response<List<Donacija>>) {
                if(response.isSuccessful){
                    val list = response.body()!!
                    myAdapterZahtjeviBenefiktoraLista.submitList(list)
                    myAdapterZahtjeviBenefiktoraLista.notifyDataSetChanged()
                }
                else{
                    Toast.makeText(this@ZahtjeviBenefiktoraListaActivity,"Server error", Toast.LENGTH_SHORT).show()
                }
                loading.stopDialog()
            }
        })
    }

    override fun <T> onItemClick(item: T, position: Int) {
        TODO("Not yet implemented")
    }
}