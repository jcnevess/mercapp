package br.com.wildsnow.mercapp.ui.home

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import br.com.wildsnow.mercapp.R
import br.com.wildsnow.mercapp.databinding.FragmentHomeBinding
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val homeViewModel = HomeViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        binding.homeViewModel = homeViewModel

        binding.quantityPicker.minValue = 0

        binding.quantityPicker.apply {
            minValue = 0
            maxValue = Int.MAX_VALUE
            wrapSelectorWheel = false
            setOnValueChangedListener { picker, oldValue, newValue ->  Timber.d("Value changed")}
        }

        val adapter = HomeListAdapter()
        binding.items.adapter = adapter

        homeViewModel.items.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }

        homeViewModel.addItemEvent.observe(viewLifecycleOwner) { isAddingItem ->
            if(!isAddingItem) {
                binding.apply {
                    quantityPicker.visibility = View.INVISIBLE
                    quantityPicker.value = 0
                    productNameEdit.visibility = View.INVISIBLE
                    productNameEdit.text = Editable.Factory().newEditable("")
                    addItemButton.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_add_24, activity?.theme))
                    addItemButton.setOnClickListener { homeViewModel!!.startAddingItem() }
                }
            } else {
                binding.apply {
                    quantityPicker.visibility = View.VISIBLE
                    productNameEdit.visibility = View.VISIBLE
                    addItemButton.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_done_24, activity?.theme))
                    addItemButton.setOnClickListener {
                        homeViewModel!!.doneAddingItem(productNameEdit.text.toString(), quantityPicker.value)
                    }
                }
            }
        }

        return binding.root
    }
}