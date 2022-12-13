package br.com.wildsnow.mercapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class HomeViewModel: ViewModel(){

    val name = "Grocery List"

    private val _items = MutableLiveData<MutableList<String>>(mutableListOf())
    val items: LiveData<MutableList<String>>
        get() = _items

    fun addItem() {
        _items.value?.add("New item")
        Timber.d("List now has ${(_items.value?.size) ?: -1} items")
    }

}
