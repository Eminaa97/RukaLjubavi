package com.fit.ba.rukaljubavi

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import com.fit.ba.rukaljubavi.Models.Benefiktor
import com.fit.ba.rukaljubavi.Models.Grad
import com.fit.ba.rukaljubavi.Services.APIService
import com.fit.ba.rukaljubavi.Services.GradService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BenefiktoriFilterDialog(var activity: Activity) {

    private val serviceGradovi = APIService.buildService(GradService::class.java)
    var dialog: AlertDialog? = null
    var txtNazivKomp: TextView? = null
    var gradovi: MutableList<Grad>? = arrayListOf()
    var spinner: Spinner? = null
    var lokacijaId: Int? = null

    fun startDialog(){
        var builder = AlertDialog.Builder(activity)
        var inflater = activity.layoutInflater
        var dialogView = inflater.inflate(R.layout.dialog_filter_benefiktor,null)

        builder.setView(dialogView)
        dialog = builder.create()
        dialog!!.show()

        var btnFilter = dialogView.findViewById<TextView>(R.id.btnFilter)
        var btnOdustani = dialogView.findViewById<TextView>(R.id.btnOdustani)
        txtNazivKomp = dialogView.findViewById<TextView>(R.id.txtNazivKompanijeFilter)

        spinner = dialogView.findViewById<Spinner>(R.id.spnBenLokacijaFilter)
        loadGradovi()
        spinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var grad = p0!!.getItemAtPosition(p2) as Grad
                lokacijaId = grad.id
            }
        }

        btnOdustani.setOnClickListener {
            dialog!!.dismiss()
        }

        btnFilter.setOnClickListener {
            load()
        }
    }

    private fun load(){
        var loading = LoadingDialog(activity)
        loading.startLoadingDialog()

        val requestCall = serviceBenefiktori.get(txtNazivKomp?.text.toString(),lokacijaId)
        requestCall.enqueue(object : Callback<List<Benefiktor>> {
            override fun onFailure(call: Call<List<Benefiktor>>, t: Throwable) {
                Toast.makeText(activity,"Error: ${t.toString()}", Toast.LENGTH_SHORT).show()
                loading.stopDialog()
            }

            override fun onResponse(call: Call<List<Benefiktor>>, response: Response<List<Benefiktor>>) {
                if(response.isSuccessful){
                    val list = response.body()!!
                    myAdapter.submitList(list)
                    myAdapter.notifyDataSetChanged()
                }
                else{
                    Toast.makeText(activity,"Server error", Toast.LENGTH_SHORT).show()
                }
                loading.stopDialog()
            }
        })
    }

    private fun loadGradovi() {
        var loading = LoadingDialog(activity)
        loading.startLoadingDialog()
        val requestCall = serviceGradovi.getAll()
        requestCall.enqueue(object : Callback<List<Grad>> {
            override fun onFailure(call: Call<List<Grad>>, t: Throwable) {
                Toast.makeText(activity,"Server error", Toast.LENGTH_SHORT).show()
                loading.stopDialog()
            }

            override fun onResponse(call: Call<List<Grad>>, response: Response<List<Grad>>) {
                if(response.isSuccessful){
                    var list = response.body()
                    gradovi = list!!.toMutableList()
                    gradovi!!.add(0,Grad(-1,"Lokacija"))
                    var adapter = object : ArrayAdapter<Grad>(activity,R.layout.spinner_list_item,gradovi!!){
                        override fun isEnabled(position: Int): Boolean {
                            return position != 0
                        }

                        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                            val view = super.getDropDownView(position, convertView, parent)
                            val tv = view as TextView
                            if (position === 0) {
                                tv.setTextColor(Color.GRAY)
                            } else {
                                tv.setTextColor(Color.BLACK)
                            }
                            return view
                        }
                    }
                    adapter.setDropDownViewResource(R.layout.spinner_item)
                    spinner!!.adapter = adapter
                }
                loading.stopDialog()
            }
        })
    }
}