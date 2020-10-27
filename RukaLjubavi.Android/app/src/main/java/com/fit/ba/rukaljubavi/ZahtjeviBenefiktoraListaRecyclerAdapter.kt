package com.fit.ba.rukaljubavi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fit.ba.rukaljubavi.Helper.OnItemClickListener
import com.fit.ba.rukaljubavi.Models.Benefiktor
import com.fit.ba.rukaljubavi.Models.Donacija
import kotlinx.android.synthetic.main.benefiktori_lista_item.view.*
import kotlinx.android.synthetic.main.benefiktori_lista_item.view.txtGrad
import kotlinx.android.synthetic.main.benefiktori_lista_item.view.txtNaziv
import kotlinx.android.synthetic.main.zahtjevi_benefiktora_lista_item.view.*

class ZahtjeviBenefiktoraListaRecyclerAdapter(var clickListener: OnItemClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Donacija> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BenefiktorViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.zahtjevi_benefiktora_lista_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is BenefiktorViewHolder ->{
                holder.bind(items[position], clickListener)
            }
        }
    }

    fun submitList(list: List<Donacija>){
        items = list
    }

    class BenefiktorViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val nazivBenefiktora = itemView.txtNaziv
        val grad = itemView.txtGrad
        val kategorija = itemView.txtKategorija

        fun bind(donacija: Donacija, action: OnItemClickListener){

            nazivBenefiktora.text = donacija.benefiktorNazivKompanije
            grad.text = donacija.benefiktorLokacija
            kategorija.text = "Potrebno: " + donacija.nazivKategorije

            itemView.setOnClickListener {
                action.onItemClick(donacija, adapterPosition)
            }
        }
    }
}