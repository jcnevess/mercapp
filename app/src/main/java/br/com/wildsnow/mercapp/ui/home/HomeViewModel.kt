package br.com.wildsnow.mercapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    val name = "Grocery List"

    private val _items: MutableLiveData<List<CartItem>> = MutableLiveData(listOf())
    val items: LiveData<List<CartItem>>
        get() = _items

    private val _addItemEvent: MutableLiveData<Boolean> = MutableLiveData(false)
    val addItemEvent: LiveData<Boolean>
        get() = _addItemEvent

    fun startAddingItem() {
        _addItemEvent.value = true
    }

    fun doneAddingItem(productName: String, productAmount: Int) {
        val item = CartItem((_items.value?.size ?: 0) + 1L, productName, productAmount, 0.0)
        _items.value = _items.value?.plus(item)
        _addItemEvent.value = false
    }

    fun editItemName(position: Int, newName: String) {
        _items.value = _items.value?.let {
            it.slice(0 until position)
                .plus(it[position].copy(name = newName))
                .plus(it.slice(position + 1 until it.size))
        }
    }

    fun editItemUnitPrice(position: Int, newPrice: Double) {
        _items.value = _items.value?.let {
            it.slice(0 until position)
                .plus(it[position].copy(unitPrice = newPrice))
                .plus(it.slice(position + 1 until it.size))
        }
    }

    fun editItemAmount(position: Int, newAmount: Int) {
        _items.value = _items.value?.let {
            it.slice(0 until position)
                .plus(it[position].copy(amount = newAmount))
                .plus(it.slice(position + 1 until it.size))
        }
    }

    fun getTotalPrice(): Double {
        return _items.value?.map { it.getTotalPrice() }
            ?.reduceOrNull { a, b -> a.plus(b) }
            ?: 0.0
    }

}
