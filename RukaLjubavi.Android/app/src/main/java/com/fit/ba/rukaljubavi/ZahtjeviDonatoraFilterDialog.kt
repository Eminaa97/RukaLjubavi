package com.fit.ba.rukaljubavi

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.fit.ba.rukaljubavi.Models.Donacija
import com.fit.ba.rukaljubavi.Models.Grad
import com.fit.ba.rukaljubavi.Models.Kategorija
import com.fit.ba.rukaljubavi.Models.StatusDonacije
import com.fit.ba.rukaljubavi.Services.APIService
import com.fit.ba.rukaljubavi.Services.GradService
import com.fit.ba.rukaljubavi.Services.KategorijaService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ZahtjeviDonatoraFilterDialog(var activity: Activity) {

    private val serviceGradovi = APIService.buildService(GradService::class.java)
    private val serviceKategorija = APIService.buildService(KategorijaService::class.java)
    var dialog: AlertDialog? = null
    var txtNazivKomp: TextView? = null
    var gradovi: MutableList<Grad>? = arrayListOf()
    var kategorije: MutableList<Kategorija>? = arrayListOf()
    var spinnerGradovi: Spinner? = null
    var spinnerKategorije: Spinner? = null
    var lokacijaId: Int? = null
    var kategorijaId: Int? = null

    fun startDialog(){
        var builder = AlertDialog.Builder(activity)
        var inflater = activity.layoutInflater
        var dialogView = inflater.inflate(R.layout.dialog_filter_zahtjevi_donatora,null)

        builder.setView(dialogView)
        dialog = builder.create()
        dialog!!.show()

        var btnFilter = dialogView.findViewById<TextView>(R.id.btnFilter)
        var btnOdustani = dialogView.findViewById<TextView>(R.id.btnOdustani)
        txtNazivKomp = dialogView.findViewById<TextView>(R.id.txtNazivKompanijeFilter)

        spinnerGradovi = dialogView.findViewById<Spinner>(R.id.spnBenLokacijaFilter)
        loadGradovi()
        spinnerGradovi!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var grad = p0!!.getItemAtPosition(p2) as Grad
                lokacijaId = grad.id
            }
        }

        spinnerKategorije = dialogView.findViewById<Spinner>(R.id.spnKategorijaFilter)
        loadKategorije()
        spinnerKategorije!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var kategorija = p0!!.getItemAtPosition(p2) as Kategorija
                kategorijaId = kategorija.id
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

        val requestCall = serviceDonacije.get(StatusDonacije = StatusDonacije.Na_cekanju,KategorijaId =  if(kategorijaId == -1) null else kategorijaId, LokacijaDonatorId =  if(lokacijaId == -1) null else lokacijaId)
        requestCall.enqueue(object : Callback<List<Donacija>> {
            override fun onFailure(call: Call<List<Donacija>>, t: Throwable) {
                Toast.makeText(activity,"Error: ${t.toString()}", Toast.LENGTH_SHORT).show()
                loading.stopDialog()
            }

            override fun onResponse(call: Call<List<Donacija>>, response: Response<List<Donacija>>) {
                if(response.isSuccessful){
                    val list = response.body()!!
                    myAdapterZahtjeviDonatora.submitList(list)
                    myAdapterZahtjeviDonatora.notifyDataSetChanged()
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
                    gradovi!!.add(0, Grad(-1,"Lokacija"))
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
                    spinnerGradovi!!.adapter = adapter
                }
                loading.stopDialog()
            }
        })
    }

    private fun loadKategorije() {
        var loading = LoadingDialog(activity)
        loading.startLoadingDialog()
        val requestCall = serviceKategorija.getAll()
        requestCall.enqueue(object : Callback<List<Kategorija>> {
            override fun onFailure(call: Call<List<Kategorija>>, t: Throwable) {
                Toast.makeText(activity,"Server error", Toast.LENGTH_SHORT).show()
                loading.stopDialog()
            }

            override fun onResponse(call: Call<List<Kategorija>>, response: Response<List<Kategorija>>) {
                if(response.isSuccessful){
                    var list = response.body()
                    kategorije = list!!.toMutableList()
                    kategorije!!.add(0, Kategorija(-1,"Kategorija"))
                    var adapter = object : ArrayAdapter<Kategorija>(activity,R.layout.spinner_list_item,kategorije!!){
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
                    spinnerKategorije!!.adapter = adapter
                }
                loading.stopDialog()
            }
        })
    }
}