package br.com.wildsnow.mercapp.ui.home

import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.wildsnow.mercapp.R
import br.com.wildsnow.mercapp.databinding.ListItemHomeBinding
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

class HomeListAdapter(private val homeViewModel: HomeViewModel) :
    ListAdapter<CartItem, HomeListAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), homeViewModel)
    }

    class ViewHolder private constructor(private val binding: ListItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemHomeBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }

        fun bind(item: CartItem, homeViewModel: HomeViewModel) {
            binding.product = item

            binding.itemAmountText.setOnClickListener {
                binding.itemAmountText.visibility = View.INVISIBLE
                binding.itemAmountEdit.visibility = View.VISIBLE
                binding.itemAmountEdit.requestFocus()
                binding.itemAmountEdit.requestKeyboard()
            }

            binding.multiplierText.setOnClickListener {
                binding.itemAmountText.visibility = View.INVISIBLE
                binding.itemAmountEdit.visibility = View.VISIBLE
                binding.itemAmountEdit.requestFocus()
                binding.itemAmountEdit.requestKeyboard()
            }

            binding.itemAmountEdit.text = Editable.Factory().newEditable(item.amount.toString())
            binding.itemAmountEdit.setOnEditorActionListener { editText, actionId, keyEvent ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (amountIsValid(editText.text.toString())) {
                        homeViewModel.editItemAmount(
                            adapterPosition,
                            editText.text.toString().toInt()
                        )
                        binding.itemAmountEdit.visibility = View.INVISIBLE
                        binding.itemAmountText.visibility = View.VISIBLE
                        return@setOnEditorActionListener true
                    }
                }
                return@setOnEditorActionListener false
            }

            binding.itemNameText.setOnClickListener {
                binding.itemNameText.visibility = View.INVISIBLE
                binding.itemNameEdit.visibility = View.VISIBLE
            }

            binding.itemNameEdit.setOnEditorActionListener { editText, actionId, keyEvent ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (nameIsValid(editText.text.toString())) {
                        homeViewModel.editItemName(
                            adapterPosition,
                            editText.text.toString()
                        )
                        binding.itemNameEdit.visibility = View.INVISIBLE
                        binding.itemNameText.visibility = View.VISIBLE
                        return@setOnEditorActionListener true
                    } else {
                        showErrorMessage()
                    }
                }
                return@setOnEditorActionListener false
            }

            binding.itemUnitPriceText.setOnClickListener {
                binding.itemUnitPriceText.visibility = View.INVISIBLE
                binding.itemUnitPriceEdit.visibility = View.VISIBLE
            }

            binding.itemUnitPriceEdit.setOnEditorActionListener { editText, actionId, keyEvent ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (priceIsValid(editText.text.toString())) {
                        homeViewModel.editItemUnitPrice(
                            adapterPosition,
                            binding.itemUnitPriceEdit.text.toString().replace(",", ".").toDouble()
                        )
                        binding.itemUnitPriceEdit.visibility = View.INVISIBLE
                        binding.itemUnitPriceText.visibility = View.VISIBLE
                        return@setOnEditorActionListener true
                    } else {
                        showErrorMessage()
                    }
                }
                return@setOnEditorActionListener false
            }

        }

        private fun amountIsValid(amount: String?): Boolean {
            return !amount.isNullOrBlank()
        }

        private fun nameIsValid(productName: String?): Boolean {
            return !productName.isNullOrBlank()
        }

        private fun priceIsValid(price: String?): Boolean {
            return !price.isNullOrBlank() && price.matches(Regex("^[0-9]*[.,][0-9]*$|^[0-9]*$"))
        }

        private fun showErrorMessage() {
            Snackbar.make(
                itemView,
                R.string.invalid_input_error,
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<CartItem>() {
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem == newItem
        }
    }
}