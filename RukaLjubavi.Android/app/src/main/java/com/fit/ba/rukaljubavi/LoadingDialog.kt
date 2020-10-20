package com.fit.ba.rukaljubavi

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.view.LayoutInflater
import android.view.View.inflate

class LoadingDialog(var activity:Activity) {

    var dialog: AlertDialog? = null

    fun startLoadingDialog(){
        var builder = AlertDialog.Builder(activity)
        var inflater = activity.layoutInflater
        var dialogView = inflater.inflate(R.layout.activity_loading,null)

        builder.setView(dialogView)
        builder.setCancelable(false)
        dialog = builder.create()
        dialog!!.show()
    }
    fun stopDialog(){
        dialog!!.dismiss()
    }
}