package br.com.wildsnow.mercapp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.wildsnow.mercapp.databinding.ListItemHomeBinding

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

            binding.itemNameEdit.setOnEditorActionListener { textView, actionId, keyEvent ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    homeViewModel.editItemName(adapterPosition, binding.itemNameEdit.text.toString())
                    binding.itemNameEdit.visibility = View.INVISIBLE
                    binding.itemNameText.visibility = View.VISIBLE
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

            binding.itemNameText.setOnClickListener {
                binding.itemNameText.visibility = View.INVISIBLE
                binding.itemNameEdit.visibility = View.VISIBLE
            }
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