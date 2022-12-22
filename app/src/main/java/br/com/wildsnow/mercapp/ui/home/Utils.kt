package br.com.wildsnow.mercapp.ui.home

import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat

fun EditText.requestKeyboard() {
    if (isFocused) {
        val imm = ContextCompat.getSystemService(rootView.context, InputMethodManager::class.java)
        imm?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}