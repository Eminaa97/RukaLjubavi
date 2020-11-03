package com.fit.ba.rukaljubavi

import android.app.Activity
import android.app.AlertDialog
import android.view.View
import android.widget.*
import com.fit.ba.rukaljubavi.Models.Donacija
import com.fit.ba.rukaljubavi.Models.StatusDonacije
import com.fit.ba.rukaljubavi.Services.APIService
import com.fit.ba.rukaljubavi.Services.DonacijaService
import com.fit.ba.rukaljubavi.Services.KategorijaService
import kotlinx.android.synthetic.main.activity_vase_donacije.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PromjeniStatusDialog(var activity: Activity, var donacijaId: Int, var trenutniStatus: String?) {

    var dialog: AlertDialog? = null
    var status: StatusDonacije? = null
    var kategorije: MutableList<VaseDonacijeActivity.DonacijaStatus>? = arrayListOf()
    var spinner: Spinner? = null
    private val service = APIService.buildService(DonacijaService::class.java)

    fun startPromjeniStatusDialog(){
        var builder = AlertDialog.Builder(activity)
        var inflater = activity.layoutInflater
        var dialogView = inflater.inflate(R.layout.dialog_promjeni_status,null)

        builder.setView(dialogView)
        dialog = builder.create()
        dialog!!.show()

        var btnFilter = dialogView.findViewById<TextView>(R.id.btnFilter)
        var btnOdustani = dialogView.findViewById<TextView>(R.id.btnOdustani)

        btnOdustani.setOnClickListener {
            dialog!!.dismiss()
        }

        btnFilter.setOnClickListener {
            load()
        }

        spinner = dialogView.findViewById<Spinner>(R.id.spnStatusFilter)
        loadKategorije()
        spinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var s = p0!!.getItemAtPosition(p2) as VaseDonacijeActivity.DonacijaStatus
                status = s.status
            }
        }
    }

    private fun loadKategorije() {
        kategorije!!.add(0, VaseDonacijeActivity.DonacijaStatus(3, StatusDonacije.Prihvacena, "Prihvacena"))
        kategorije!!.add(1, VaseDonacijeActivity.DonacijaStatus(4, StatusDonacije.Odbijena, "Odbijena"))
        kategorije!!.add(2, VaseDonacijeActivity.DonacijaStatus(5, StatusDonacije.U_toku, "U_toku"))
        kategorije!!.add(3, VaseDonacijeActivity.DonacijaStatus(6, StatusDonacije.Zavrsena, "Zavrsena"))

        var adapter = ArrayAdapter<VaseDonacijeActivity.DonacijaStatus>(activity, R.layout.layout_spinner_item, kategorije!!)
        adapter.setDropDownViewResource(R.layout.spinner_item)
        spinner!!.adapter = adapter
        var index: Int = getIndex(spinner!!, trenutniStatus!!)
        spinner!!.setSelection(index)
    }

    private fun getIndex(spinner: Spinner, myString: String): Int {
        var index = 0
        for (i in 0 until spinner.count) {
            val temp: VaseDonacijeActivity.DonacijaStatus = spinner.getItemAtPosition(i) as VaseDonacijeActivity.DonacijaStatus
            if (temp.status!!.name == myString) {
                index = i
            }
        }
        return index
    }

    private fun load(){
        var loading = LoadingDialog(activity)
        loading.startLoadingDialog()

        val requestCall = service.changeStatus(APIService.loggedUserToken, donacijaId, status!!)
        requestCall.enqueue(object : Callback<Unit> {
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Toast.makeText(activity,"Error: ${t.toString()}", Toast.LENGTH_SHORT).show()
                loading.stopDialog()
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(response.isSuccessful){
                    Toast.makeText(activity,"Status promjenjen.", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(activity,"Server error", Toast.LENGTH_SHORT).show()
                }
                loading.stopDialog()
            }
        })
    }
}