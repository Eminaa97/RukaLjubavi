package com.fit.ba.rukaljubavi

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.fit.ba.rukaljubavi.Models.Drzava
import com.fit.ba.rukaljubavi.Models.Grad
import com.fit.ba.rukaljubavi.Requests.BenefiktorInsertRequest
import com.fit.ba.rukaljubavi.Requests.DonatorInsertRequest
import com.fit.ba.rukaljubavi.Services.APIService
import com.fit.ba.rukaljubavi.Services.DonatorService
import com.fit.ba.rukaljubavi.Services.GradService
import kotlinx.android.synthetic.main.activity_registracija_benefiktor.*
import kotlinx.android.synthetic.main.activity_registracija_donator.*
import kotlinx.android.synthetic.main.activity_registracija_donator.btnBack4
import kotlinx.android.synthetic.main.activity_registracija_donator.txtRegAdresa
import kotlinx.android.synthetic.main.activity_registracija_donator.txtRegEmail
import kotlinx.android.synthetic.main.activity_registracija_donator.txtRegPassword
import kotlinx.android.synthetic.main.activity_registracija_donator.txtRegPasswordPotvrda
import kotlinx.android.synthetic.main.activity_registracija_donator.txtRegTelefon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.Year
import java.util.*

class RegistracijaDonatorActivity : AppCompatActivity() {

    private val service = APIService.buildService(DonatorService::class.java)
    private val serviceGradovi = APIService.buildService(GradService::class.java)
    var spinnerMjestoRodjenja: Spinner? = null
    var spinnerMjestoPrebivalista: Spinner? = null
    var donator = DonatorInsertRequest()
    var datePicker: DatePickerDialog.OnDateSetListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registracija_donator)

        btnBack4.setOnClickListener {
            val intent = Intent(this,RegistracijaIzborActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnRegistracijaDonatorDalje.setOnClickListener {
            sendNoviDonator()
        }

        txtRegDatumRodjenja.isFocusable = false
        txtRegDatumRodjenja.isClickable = true
        txtRegDatumRodjenja.inputType = InputType.TYPE_NULL
        txtRegDatumRodjenja.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var calendar: Calendar = Calendar.getInstance()
                var year: Int = calendar.get(Calendar.YEAR)
                var month: Int = calendar.get(Calendar.MONTH)
                var day: Int = calendar.get(Calendar.DAY_OF_MONTH)

                var dpd: DatePickerDialog = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    DatePickerDialog(
                        this@RegistracijaDonatorActivity,
                        android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                        datePicker,
                        year,month,day
                    )
                } else {
                    TODO("VERSION.SDK_INT < N")
                }

                dpd.window!!.setBackgroundDrawable(ColorDrawable(Color.BLACK))
                dpd.show()
            }
        })

        datePicker = object : DatePickerDialog.OnDateSetListener{
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                //donator.datumRodjenja = """$year-$month-${dayOfMonth}"""
                txtRegDatumRodjenja.setText("""$dayOfMonth.$month.${year}""")
            }
        }

        spinnerMjestoPrebivalista = spnDonMjestoPrebivalista
        spinnerMjestoRodjenja = spnDonMjestoRodjenja
        loadGradovi()
        spinnerMjestoPrebivalista!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var grad = p0!!.getItemAtPosition(p2) as Grad
                donator.mjestoPrebivalistaId = grad.id
            }
        }
        spinnerMjestoRodjenja!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var grad = p0!!.getItemAtPosition(p2) as Grad
                donator.mjestoRodjenjaId = grad.id
            }
        }
    }

    private fun loadGradovi() {
        var loading = LoadingDialog(this@RegistracijaDonatorActivity)
        loading.startLoadingDialog()
        val requestCall = serviceGradovi.getAll()
        requestCall.enqueue(object : Callback<List<Grad>> {
            override fun onFailure(call: Call<List<Grad>>, t: Throwable) {
                Toast.makeText(this@RegistracijaDonatorActivity,"Server error", Toast.LENGTH_SHORT).show()
                loading.stopDialog()
            }

            override fun onResponse(call: Call<List<Grad>>, response: Response<List<Grad>>) {
                if(response.isSuccessful){
                    var list = response.body()
                    var gradoviMR = list!!.toMutableList()
                    var gradoviMP = list!!.toMutableList()
                    initMjestoRodjenjaSpinner(gradoviMR)
                    initMjestoPrebivalistaSpinner(gradoviMP)
                }
                loading.stopDialog()
            }
        })
    }

    private fun initMjestoRodjenjaSpinner(gradovi: MutableList<Grad>?){
        gradovi!!.add(0, Grad(-1,"Mjesto rođenja"))
        var adapter = object : ArrayAdapter<Grad>(this@RegistracijaDonatorActivity,R.layout.spinner_list_item,gradovi!!){
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
        spinnerMjestoRodjenja!!.adapter = adapter
    }

    private fun initMjestoPrebivalistaSpinner(gradovi: MutableList<Grad>?){
        gradovi!!.add(0, Grad(-1,"Mjesto prebivalista"))
        var adapter = object : ArrayAdapter<Grad>(this@RegistracijaDonatorActivity,R.layout.spinner_list_item,gradovi!!){
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
        spinnerMjestoPrebivalista!!.adapter = adapter
    }

    private fun sendNoviDonator() {
        donator.ime = txtRegIme!!.text.toString()
        donator.prezime = txtRegPrezime!!.text.toString()
        donator.adresa = txtRegAdresa!!.text.toString()
        donator.email = txtRegEmail!!.text.toString()
        donator.jmbg = txtRegJMBG!!.text.toString()
        donator.password = txtRegPassword!!.text.toString()
        donator.confirmPassword = txtRegPasswordPotvrda!!.text.toString()
        donator.telefon = txtRegTelefon!!.text.toString()
        donator.datumRodjenja = "2020-10-22T22:52:56.207Z"

        var error: Boolean = false

        if(donator.ime.isNullOrBlank()){
            txtRegIme.setBackgroundResource(R.drawable.input_field_error)
            error = true
        }
        else
            txtRegIme.setBackgroundResource(R.drawable.input_field)
        if(donator.prezime.isNullOrBlank()){
            txtRegPrezime.setBackgroundResource(R.drawable.input_field_error)
            error = true
        }
        else
            txtRegPrezime.setBackgroundResource(R.drawable.input_field)
        if(donator.adresa.isNullOrBlank()){
            txtRegAdresa.setBackgroundResource(R.drawable.input_field_error)
            error = true
        }
        else
            txtRegAdresa.setBackgroundResource(R.drawable.input_field)
        if(donator.email.isNullOrBlank()){
            txtRegEmail.setBackgroundResource(R.drawable.input_field_error)
            error = true
        }
        else{
            if(!TextUtils.isEmpty(donator.email) && android.util.Patterns.EMAIL_ADDRESS.matcher(donator.email).matches()){
                txtRegEmail.setBackgroundResource(R.drawable.input_field)
            }
            else{
                error = true
                txtRegEmail.setBackgroundResource(R.drawable.input_field_error)
            }
        }
        if(donator.telefon.isNullOrBlank()){
            txtRegTelefon.setBackgroundResource(R.drawable.input_field_error)
            error = true
        }
        else
            txtRegTelefon.setBackgroundResource(R.drawable.input_field)
        if(donator.jmbg.isNullOrBlank()){
            txtRegJMBG.setBackgroundResource(R.drawable.input_field_error)
            error = true
        }
        else{
            if(donator.jmbg!!.length == 13){
                txtRegJMBG.setBackgroundResource(R.drawable.input_field)
            }
            else{
                error = true
                txtRegJMBG.setBackgroundResource(R.drawable.input_field_error)
                txtRegJMBG.hint = "Tačno 13 karaktera."
            }
        }
        if(donator.password.isNullOrBlank()){
            txtRegPassword.setBackgroundResource(R.drawable.input_field_error)
            error = true
        }
        else{
            if(donator.password!!.length >= 5){
                txtRegPassword.setBackgroundResource(R.drawable.input_field)
            }
            else{
                error = true
                txtRegPassword.setBackgroundResource(R.drawable.input_field_error)
                txtRegPassword.hint = "Minimalno 5 karaktera."
            }
        }
        if(donator.confirmPassword.isNullOrBlank()){
            txtRegPasswordPotvrda.setBackgroundResource(R.drawable.input_field_error)
            error = true
        }
        else{
            if(donator.password!!.length >= 5 && donator.confirmPassword!! == donator.password){
                txtRegPasswordPotvrda.setBackgroundResource(R.drawable.input_field)
            }
            else{
                error = true
                txtRegPasswordPotvrda.setBackgroundResource(R.drawable.input_field_error)
                txtRegPasswordPotvrda.hint = "Lozinke se ne slažu."
            }
        }
        if(donator.datumRodjenja.isNullOrBlank()){
            txtRegDatumRodjenja.setBackgroundResource(R.drawable.input_field_error)
            error = true
        }
        else
            txtRegDatumRodjenja.setBackgroundResource(R.drawable.input_field)
        if(donator.mjestoPrebivalistaId == -1){
            spnDonMjestoPrebivalista.setBackgroundResource(R.drawable.spiner_field_error)
            error = true
        }
        else
            spnDonMjestoPrebivalista.setBackgroundResource(R.drawable.spiner_field)
        if(donator.mjestoRodjenjaId == -1){
            spnDonMjestoRodjenja.setBackgroundResource(R.drawable.spiner_field_error)
            error = true
        }
        else
            spnDonMjestoRodjenja.setBackgroundResource(R.drawable.spiner_field)

        if(!error) {
            val intent = Intent(this, DonatorKategorijeActivity::class.java)
            intent.putExtra("NEW_DONATOR", donator)
            startActivity(intent)
        }
    }
}