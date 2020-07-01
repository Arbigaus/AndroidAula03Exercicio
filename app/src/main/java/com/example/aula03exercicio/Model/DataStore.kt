package com.example.aula03exercicio.Model

import android.content.Context

object DataStore {

    var shopList: MutableList<ShopItem> = arrayListOf()
        private set

    private var myContext: Context? = null

    fun setContext(value: Context) {
        myContext = value
    }

    init {
        addShopItem(ShopItem("Arroz",1,2.0, 2.0))
        addShopItem(ShopItem("Feijão",1,2.5, 2.5))
        addShopItem(ShopItem("Macarrão",1,3.25, 3.25))
        addShopItem(ShopItem("Chocolate",2,4.70, 9.40))
        addShopItem(ShopItem("Yogurte",4,1.89, 7.56))
        addShopItem(ShopItem("Heyneken",12,3.49, 41.88))
    }

    fun getShopItem(position: Int): ShopItem {
        return shopList.get(position)
    }

    fun addShopItem(item: ShopItem) {
        shopList.add(item)
    }

    fun editShopItem(item: ShopItem, position: Int) {
        shopList.set(position, item)
    }

    fun removeShopItem(position: Int) {
        shopList.removeAt(position)
    }

    fun clearShopItem() {
        shopList.clear()
    }

}