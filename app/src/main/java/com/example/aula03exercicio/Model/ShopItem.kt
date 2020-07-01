package com.example.aula03exercicio.Model

class ShopItem(
    var itemName: String,
    var itemQtd: Int,
    var itemPrice: Double,
    var itemTotalPrice: Double
) {

    constructor() : this("", 0,0.0, 0.0)
}