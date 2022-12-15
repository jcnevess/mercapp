package br.com.wildsnow.mercapp.ui.home

data class CartItem(val id: Long,
                    var quantity: Int,
                    var name: String,
                    var unitPrice: Double) {

    fun getTotalPrice() = quantity * unitPrice
}
