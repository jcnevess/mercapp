package br.com.wildsnow.mercapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class HomeViewModel: ViewModel(){

    val name = "Grocery List"

    private val _items: MutableLiveData<List<String>> = MutableLiveData(listOf())
    val items: LiveData<List<String>>
        get() = _items

    fun addItem() {
        _items.postValue(_items.value!!.plus("New item"))
        Timber.d("List now has ${(_items.value?.size) ?: -1} items")
    }

}
