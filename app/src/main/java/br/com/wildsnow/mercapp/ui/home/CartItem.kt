package br.com.wildsnow.mercapp.ui.home

data class CartItem(
    var id: Long,
    var name: String,
    var amount: Int,
    var unitPrice: Double?,
    var bought: Boolean = false
) {

    fun getTotalPrice() = unitPrice?.times(amount)
}
