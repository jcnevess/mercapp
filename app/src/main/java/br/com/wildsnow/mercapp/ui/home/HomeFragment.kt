package br.com.wildsnow.mercapp.ui.home

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import br.com.wildsnow.mercapp.R
import br.com.wildsnow.mercapp.databinding.FragmentHomeBinding


private const val QUANTITY_PICKER_MIN_VALUE = 1
private const val QUANTITY_PICKER_MAX_VALUE = Int.MAX_VALUE
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
            minValue = QUANTITY_PICKER_MIN_VALUE
            maxValue = QUANTITY_PICKER_MAX_VALUE
            wrapSelectorWheel = false
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
                    quantityPicker.value = QUANTITY_PICKER_MIN_VALUE
                    productNameEdit.visibility = View.INVISIBLE
                    productNameEdit.text = Editable.Factory().newEditable("")
                    addItemButton.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_add_24, activity?.theme))
                    addItemButton.setOnClickListener { homeViewModel!!.startAddingItem() }

                    // Hide keyboard
                    view?.let {
                        val imm = getSystemService(requireContext(), InputMethodManager::class.java)
                        imm?.hideSoftInputFromWindow(it.windowToken, 0)
                    }
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