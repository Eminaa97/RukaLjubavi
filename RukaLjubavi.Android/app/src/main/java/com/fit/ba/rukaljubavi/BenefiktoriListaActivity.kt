package com.fit.ba.rukaljubavi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.fit.ba.rukaljubavi.Helper.TopSpancingItemDecoration
import com.fit.ba.rukaljubavi.Models.Benefiktor
import com.fit.ba.rukaljubavi.Services.APIService
import com.fit.ba.rukaljubavi.Services.BenefiktorService
import com.fit.ba.rukaljubavi.Services.DonatorService
import kotlinx.android.synthetic.main.activity_benefiktori_lista.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BenefiktoriListaActivity : AppCompatActivity() {

    private val service = APIService.buildService(BenefiktorService::class.java)
    private lateinit var myAdapter: BenefiktoriListaRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_benefiktori_lista)
        title = "Benefiktori"
        initRecyclerView()
        load()
    }

    private fun load(){
        var loading = LoadingDialog(this@BenefiktoriListaActivity)
        loading.startLoadingDialog()

        val requestCall = service.get(1)
        requestCall.enqueue(object : Callback<Benefiktor> {
            override fun onFailure(call: Call<Benefiktor>, t: Throwable) {
                Toast.makeText(this@BenefiktoriListaActivity,"Error: ${t.toString()}", Toast.LENGTH_SHORT).show()
                loading.stopDialog()
            }

            override fun onResponse(call: Call<Benefiktor>, response: Response<Benefiktor>) {
                if(response.isSuccessful){
                    val list = response.body()!!
                    var items: MutableList<Benefiktor> = ArrayList()
                    items.add(Benefiktor("Kompanija 1","10203040506708","email.mail@mail.com","023456789","Neka adresa 77","Mostar", "2020-10-20"))
                    items.add(list)
                    items.add(list)
                    myAdapter.submitList(items)
                    myAdapter.notifyDataSetChanged()
                }
                else{
                    Toast.makeText(this@BenefiktoriListaActivity,"Server error", Toast.LENGTH_SHORT).show()
                }
                loading.stopDialog()
            }
        })
    }

    private fun initRecyclerView(){
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@BenefiktoriListaActivity)
            val topSpacingDecoration =
                TopSpancingItemDecoration(30)
            addItemDecoration(topSpacingDecoration)
            myAdapter = BenefiktoriListaRecyclerAdapter()
            adapter = myAdapter
        }
    }
}