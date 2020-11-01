package com.fit.ba.rukaljubavi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fit.ba.rukaljubavi.Helper.OnItemClickListener
import com.fit.ba.rukaljubavi.Models.Donacija
import kotlinx.android.synthetic.main.activity_donacija_detalji.*
import kotlinx.android.synthetic.main.vase_donacije_list_item.view.*

class VaseDonacijeRecyclerAdapter(var clickListener: OnItemClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<Donacija> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return VaseDonacijeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.vase_donacije_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is VaseDonacijeViewHolder ->{
                holder.bind(items[position], clickListener)
            }
        }
    }

    fun submitList(list: List<Donacija>){
        items = list
    }

    class VaseDonacijeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val nazivBenefiktora = itemView.txtNaziv
        val grad = itemView.txtGrad
        val kategorija = itemView.txtKategorija
        val status = itemView.txtStatus

        fun bind(donacija: Donacija, action: OnItemClickListener){

            nazivBenefiktora.text = donacija.nazivKategorije

            if(donacija.benefiktorNazivKompanije.isNullOrBlank()){
                grad.text = donacija.donatorIme+" "+donacija.donatorPrezime
            }
            else{
                grad.text = donacija.benefiktorNazivKompanije + ", "+donacija.benefiktorLokacija
            }
            var dan = donacija!!.datumVrijeme.substring(8,10)
            var mjesec = donacija!!.datumVrijeme.substring(5,7)
            var godina = donacija!!.datumVrijeme.substring(0,4)
            kategorija.text = "$dan.$mjesec.$godina"
            status.text = donacija!!.status

            itemView.setOnClickListener {
                action.onItemClick(donacija, adapterPosition)
            }
        }
    }
}