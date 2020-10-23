package com.fit.ba.rukaljubavi

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import kotlinx.android.synthetic.main.activity_alert_dialog.*

class AlertDialog(var activity: Activity) {
    var dialog: AlertDialog? = null
    fun<T> startAlertDialog(someClass: Class<T>?){
        var builder = AlertDialog.Builder(activity)
        var inflater = activity.layoutInflater
        var dialogView = inflater.inflate(R.layout.activity_alert_dialog,null)

        builder.setView(dialogView)
        dialog = builder.create()
        dialog!!.show()

        var btnAlertNE = dialogView.findViewById<TextView>(R.id.btnAlertNE)
        var btnAlertDA = dialogView.findViewById<TextView>(R.id.btnAlertDA)

        btnAlertNE.setOnClickListener {
            dialog!!.dismiss()
        }

        btnAlertDA.setOnClickListener {
            val intent = Intent(activity,someClass)
            startActivity(activity,intent,null)
            activity.finish()
        }
    }
}