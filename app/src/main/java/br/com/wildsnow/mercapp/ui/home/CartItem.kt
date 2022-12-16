package br.com.wildsnow.mercapp.ui.home

data class CartItem(var name: String,
                    var quantity: Int,
                    var unitPrice: Double?) {

    fun getTotalPrice() = unitPrice?.times(quantity)
}
