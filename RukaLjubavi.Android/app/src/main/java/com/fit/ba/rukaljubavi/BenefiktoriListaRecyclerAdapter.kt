package com.fit.ba.rukaljubavi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fit.ba.rukaljubavi.Models.Benefiktor
import com.fit.ba.rukaljubavi.Models.Donator
import kotlinx.android.synthetic.main.benefiktori_lista_item.view.*

class BenefiktoriListaRecyclerAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Benefiktor> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BenefiktorViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.benefiktori_lista_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is BenefiktorViewHolder ->{
                holder.bind(items[position])
            }
        }
    }

    fun submitList(list: List<Benefiktor>){
        items = list
    }

    class BenefiktorViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val nazivBenefiktora = itemView.txtNaziv
        val grad = itemView.txtGrad
        val email = itemView.txtEmail
        val telefon = itemView.txtTelefon

        fun bind(benefiktor: Benefiktor){
            nazivBenefiktora.text = """${benefiktor.nazivKompanije} | ${benefiktor.pdvbroj}"""
            grad.text = """${benefiktor.mjestoPrebivalista}, ${benefiktor.adresa}"""
            email.text = benefiktor.email
            telefon.text = benefiktor.telefon
        }
    }
}