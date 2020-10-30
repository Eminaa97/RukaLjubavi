package com.fit.ba.rukaljubavi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fit.ba.rukaljubavi.Helper.OnItemClickListener
import com.fit.ba.rukaljubavi.Models.Benefiktor
import com.fit.ba.rukaljubavi.Models.Kategorija
import kotlinx.android.synthetic.main.benefiktori_lista_item.view.*
import kotlinx.android.synthetic.main.profile_kategorije_list_item.view.*

class ProfilKategorijeRecyclerAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Kategorija> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return KategorijaViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.profile_kategorije_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is KategorijaViewHolder ->{
                holder.bind(items[position])
            }
        }
    }

    fun submitList(list: List<Kategorija>){
        items = list
    }

    class KategorijaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val txtKategorijaNaziv = itemView.txtKategorijaNaziv

        fun bind(kategorija: Kategorija){
            txtKategorijaNaziv.text = kategorija.naziv
        }
    }
}