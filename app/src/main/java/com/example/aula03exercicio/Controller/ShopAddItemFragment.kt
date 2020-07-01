package com.example.aula03exercicio.Controller

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.aula03exercicio.Model.DataStore
import com.example.aula03exercicio.Model.ShopItem
import com.example.aula03exercicio.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_shop_add_item.*

class ShopAddItemFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop_add_item, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val txtItem = view.findViewById<TextView>(R.id.txtItem)
        val txtPrice = view.findViewById<TextView>(R.id.txtPrice)
        val txtQtde = view.findViewById<TextView>(R.id.txtQtd)

        val btnSave = view.findViewById<Button>(R.id.btnSave)

        val itemPosition = requireArguments().getString("itemPosition");

        if (itemPosition !== null) {
            val item = DataStore.getShopItem(itemPosition.toInt())

            Log.d("item", item.itemName)
            txtItem.setText(item.itemName)
            txtPrice.setText("${item.itemPrice}")
            txtQtde.setText("${item.itemQtd}")

        }

        btnSave.setOnClickListener {

            val itemName = txtItem.text.toString()
            var price = txtPrice.text.toString()
            var qtde = txtQtde.text.toString()

            if (itemName == "" || price == "" || qtde == "" ) {
                Snackbar.make(view, "Favor preencher os campos", Snackbar.LENGTH_LONG).show()
            } else {
                price = price.toDouble().toString()
                qtde = qtde.toInt().toString()
                val priceTotal = price.toDouble() * qtde.toDouble()

                val item = ShopItem(itemName, qtde.toInt(), price.toDouble(), priceTotal)

                if (itemPosition === null) {
                    DataStore.addShopItem(item)
                    findNavController().previousBackStackEntry?.savedStateHandle?.set("msg", "Produto ${itemName} criado.")
                } else {
                    DataStore.editShopItem(item, itemPosition.toInt())
                    findNavController().previousBackStackEntry?.savedStateHandle?.set("msg", "Produto ${itemName} atualizado.")
                }

                val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(requireView().windowToken, 0)

                findNavController().popBackStack()

            }

        }

        activity?.onBackPressedDispatcher?.addCallback {
            findNavController().previousBackStackEntry?.savedStateHandle?.set("itemName", "")
            findNavController().popBackStack()
        }

    }

}