package br.com.wildsnow.mercapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class HomeViewModel: ViewModel(){

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

    fun doneAddingItem(productName: String, productQuantity: Int) {
        val item = CartItem((_items.value?.size ?: 0) +1L, productName, productQuantity, 0.0)
        _items.value = _items.value?.plus(item)
        _addItemEvent.value = false
    }

    fun editItemName(position:Int, newName: String) {
        _items.value = _items.value?.let {
            it.slice(0 until position)
                .plus(it[position].copy(name = newName))
                .plus(it.slice(position+1 until it.size))
        }
    }

}
