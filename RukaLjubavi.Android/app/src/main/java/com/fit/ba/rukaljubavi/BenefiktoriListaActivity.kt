package com.fit.ba.rukaljubavi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.fit.ba.rukaljubavi.Helper.OnItemClickListener
import com.fit.ba.rukaljubavi.Helper.TopSpancingItemDecoration
import com.fit.ba.rukaljubavi.Models.Benefiktor
import com.fit.ba.rukaljubavi.Services.APIService
import com.fit.ba.rukaljubavi.Services.BenefiktorService
import com.fit.ba.rukaljubavi.Services.DonatorService
import kotlinx.android.synthetic.main.activity_benefiktori_lista.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

public val serviceBenefiktori = APIService.buildService(BenefiktorService::class.java)
public lateinit var myAdapter: BenefiktoriListaRecyclerAdapter

class BenefiktoriListaActivity : AppCompatActivity(), OnItemClickListener {

    var previousActivity: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_benefiktori_lista)
        title = "Benefiktori"

        previousActivity = intent.getStringExtra("ACTIVITY")

        initRecyclerView()
        load()

        btnFilter.setOnClickListener {
            BenefiktoriFilterDialog(this@BenefiktoriListaActivity).startDialog()
        }
    }

    private fun load(){
        var loading = LoadingDialog(this@BenefiktoriListaActivity)
        loading.startLoadingDialog()

        val requestCall = serviceBenefiktori.get(null, null)
        requestCall.enqueue(object : Callback<List<Benefiktor>> {
            override fun onFailure(call: Call<List<Benefiktor>>, t: Throwable) {
                Toast.makeText(this@BenefiktoriListaActivity,"Error: ${t.toString()}", Toast.LENGTH_SHORT).show()
                loading.stopDialog()
            }

            override fun onResponse(call: Call<List<Benefiktor>>, response: Response<List<Benefiktor>>) {
                if(response.isSuccessful){
                    val list = response.body()!!
                    myAdapter.submitList(list)
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
            myAdapter = BenefiktoriListaRecyclerAdapter(this@BenefiktoriListaActivity)
            adapter = myAdapter
        }
    }

    override fun <T> onItemClick(item: T, position: Int) {
        if(previousActivity.equals("NovaDonacijaActivity")){
            val intent = Intent(this, NovaDonacijaActivity::class.java)
            intent.putExtra("BENEFIKTOR",item as Benefiktor)
            intent.putExtra("ACTIVITY","BenefiktoriListaActivity")
            startActivity(intent)
            finish()
            return
        }
        val intent = Intent(this, BenefiktorProfilActivity::class.java)
        intent.putExtra("BENEFIKTOR",item as Benefiktor)
        intent.putExtra("ACTIVITY","BenefiktoriListaActivity")
        startActivity(intent)
    }
}