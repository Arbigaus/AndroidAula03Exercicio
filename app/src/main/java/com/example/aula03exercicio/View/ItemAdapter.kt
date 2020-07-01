package com.example.aula03exercicio.View

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aula03exercicio.Model.ShopItem
import com.example.aula03exercicio.R

class ItemAdapter(private var listItems: MutableList<ShopItem>)
    : RecyclerView.Adapter<ItemAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context)
                                .inflate(R.layout.rcv_items, parent, false)

        return ItemHolder(view)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = listItems.get(position)

        holder.itemName.text    = item.itemName
        holder.itemPrice.text   = "R$ ${item.itemPrice}"
        holder.itemTotal.text   = "R$ ${item.itemTotalPrice}"
        holder.itemQtde.text    = "Qtde.: ${item.itemQtd}"

    }

    inner class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemName: TextView
        var itemPrice: TextView
        var itemTotal: TextView
        var itemQtde: TextView

        init {
            itemName = view.findViewById(R.id.txtItem)
            itemPrice = view.findViewById(R.id.txtPrice)
            itemTotal = view.findViewById(R.id.txtPriceTotal)
            itemQtde = view.findViewById(R.id.txtQtd)
        }
    }

}
