package com.fit.ba.rukaljubavi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.fit.ba.rukaljubavi.Helper.OnItemClickListener
import com.fit.ba.rukaljubavi.Helper.TopSpancingItemDecoration
import com.fit.ba.rukaljubavi.Models.Donacija
import com.fit.ba.rukaljubavi.Services.APIService
import com.fit.ba.rukaljubavi.Services.DonacijaService
import kotlinx.android.synthetic.main.activity_aktivne_donacije.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

lateinit var myAdapterAktivneDonacije: AktivneDonacijeRecyclerAdapter

class AktivneDonacijeActivity : AppCompatActivity(), OnItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aktivne_donacije)

        title = "Aktivne donacije"

        initRecyclerView()
        load()

        btnFilter2.setOnClickListener {
            //ZahtjeviBenefiktoraFilterDialog(this@AktivneDonacijeActivity).startDialog()
        }
    }

    private fun initRecyclerView(){
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@AktivneDonacijeActivity)
            val topSpacingDecoration =
                TopSpancingItemDecoration(30)
            addItemDecoration(topSpacingDecoration)
            myAdapterAktivneDonacije = AktivneDonacijeRecyclerAdapter(this@AktivneDonacijeActivity)
            adapter = myAdapterAktivneDonacije
        }
    }

    private fun load(){
        var loading = LoadingDialog(this@AktivneDonacijeActivity)
        loading.startLoadingDialog()

        val requestCall = serviceDonacije.get(isZahtjevZaBenefiktora = true)
        requestCall.enqueue(object : Callback<List<Donacija>> {
            override fun onFailure(call: Call<List<Donacija>>, t: Throwable) {
                Toast.makeText(this@AktivneDonacijeActivity,"Error: ${t.toString()}", Toast.LENGTH_SHORT).show()
                loading.stopDialog()
            }

            override fun onResponse(call: Call<List<Donacija>>, response: Response<List<Donacija>>) {
                if(response.isSuccessful){
                    val list = response.body()!!
                    myAdapterAktivneDonacije.submitList(list)
                    myAdapterAktivneDonacije.notifyDataSetChanged()
                }
                else{
                    Toast.makeText(this@AktivneDonacijeActivity,"Server error", Toast.LENGTH_SHORT).show()
                }
                loading.stopDialog()
            }
        })
    }

    override fun <T> onItemClick(item: T, position: Int) {
        val intent = Intent(this, DonacijaDetaljiActivity::class.java)
        intent.putExtra("DONACIJA",item as Donacija)
        intent.putExtra("ACTIVITY","AktivneDonacijeActivity")
        startActivity(intent)
    }
}