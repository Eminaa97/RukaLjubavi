package com.fit.ba.rukaljubavi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fit.ba.rukaljubavi.Helper.OnItemClickListener
import com.fit.ba.rukaljubavi.Models.Donacija
import kotlinx.android.synthetic.main.aktivne_donacije_list_item.view.*

class AktivneDonacijeRecyclerAdapter(var clickListener: OnItemClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Donacija> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AktivneDonacijeViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.aktivne_donacije_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AktivneDonacijeViewHolder -> {
                holder.bind(items[position], clickListener)
            }
        }
    }

    fun submitList(list: List<Donacija>) {
        items = list
    }

    class AktivneDonacijeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var head = itemView.txtHead
        var sub = itemView.txtSub
        var side = itemView.txtSides

        fun bind(donacija: Donacija, action: OnItemClickListener) {

            head.text = donacija!!.nazivKategorije
            sub.text = donacija!!.donatorIme + " " + donacija!!.donatorPrezime + ", " + donacija.donatorPrebivaliste
            side.text = if(donacija!!.kolicina != 0) donacija!!.kolicina.toString() else "N/A"

            itemView.setOnClickListener {
                action.onItemClick(donacija, adapterPosition)
            }
        }
    }
}