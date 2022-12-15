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

    fun addItem() {
        _items.postValue(_items.value!!.plus(CartItem(1,2, "ultra mega giga blaster big cookie", 2.99)))
        Timber.d("List now has ${(_items.value?.size) ?: -1} items")
    }

}
