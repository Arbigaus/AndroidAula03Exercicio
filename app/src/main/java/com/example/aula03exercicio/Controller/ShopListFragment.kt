package com.example.aula03exercicio.Controller

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aula03exercicio.Model.DataStore
import com.example.aula03exercicio.R
import com.example.aula03exercicio.View.ItemAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_shop_list.*

class ShopListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_shop_list, container, false)

        val btnFab = view.findViewById<FloatingActionButton>(R.id.fab)
        btnFab.setOnClickListener {
            val directions =
                ShopListFragmentDirections.actionShopListFragmentToShopAddItemFragment()
            findNavController().navigate(directions)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rcvShopItems.apply {
            layoutManager = LinearLayoutManager(activity)
            rcvShopItems.layoutManager = layoutManager
            adapter = ItemAdapter(DataStore.shopList)
            rcvShopItems.adapter = adapter
        }

        findNavController().currentBackStackEntry?.savedStateHandle
            ?.getLiveData<String>("msg")?.observe(
                viewLifecycleOwner) { result ->
                    if (result.toString() != "" ) {
                        val snack =  Snackbar.make(view, result.toString(), Snackbar.LENGTH_LONG)
                        snack.setActionTextColor(Color.parseColor("#FFFFFF"))
                        snack.setBackgroundTint(Color.parseColor("#791E88"))
                        snack.show()
                    }
            }

        val gestureDetector = GestureDetector(activity, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {

                e?.let {
                    val view = rcvShopItems.findChildViewUnder(e.x, e.y)
                    view?.let {
                        val position = rcvShopItems.getChildAdapterPosition(view)
                        val bundle = Bundle()
                        bundle.putString("itemPosition", position.toString())
                        findNavController().navigate(R.id.action_shopListFragment_to_shopAddItemFragment, bundle)
                    }
                }
                return super.onSingleTapConfirmed(e)
            }

            override fun onLongPress(e: MotionEvent?) {
                super.onLongPress(e)

                e?.let {
                    val view = rcvShopItems.findChildViewUnder(e.x, e.y)
                    view?.let {
                        val position = rcvShopItems.getChildAdapterPosition(view)
                        val item = DataStore.getShopItem(position)
                        val dialog = activity?.let { it1 -> AlertDialog.Builder(it1) }
                        dialog?.setTitle("Lista de Compras")
                        dialog?.setMessage("Tem certeza que pretende excuir o produto ${item.itemName}?")
                        dialog?.setPositiveButton("Excluir", DialogInterface.OnClickListener { dialog, which ->
                            DataStore.removeShopItem(position)
                            rcvShopItems.adapter!!.notifyDataSetChanged()
                            val snack = Snackbar.make(view, "Produto ${item.itemName} removido.", Snackbar.LENGTH_LONG)
                            snack.setBackgroundTint(Color.parseColor("#CC0000"))
                            snack.setActionTextColor(Color.parseColor("#FFFFFF"))
                            snack.show()

                        })
                        dialog?.setNegativeButton("Cancelar", null)
                        dialog?.show()
                    }
                }
            }
        })

        rcvShopItems.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                TODO("Not yet implemented")
            }

            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                val child = rv.findChildViewUnder(e.x, e.y)
                return (child != null && gestureDetector.onTouchEvent(e))
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
                TODO("Not yet implemented")
            }

        })
    }
}