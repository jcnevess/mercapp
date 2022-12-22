package br.com.wildsnow.mercapp.ui.home

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import br.com.wildsnow.mercapp.R
import br.com.wildsnow.mercapp.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar


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

        binding.productNameEdit.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (amountIsValid() && nameIsValid()) {
                    homeViewModel.doneAddingItem(
                        binding.productNameEdit.text.toString(),
                        binding.productAmountEdit.text.toString().toInt()
                    )
                } else {
                    showErrorMessage()
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        val adapter = HomeListAdapter(homeViewModel)
        binding.items.adapter = adapter

        homeViewModel.items.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }

        homeViewModel.addItemEvent.observe(viewLifecycleOwner) { isAddingItem ->
            if (!isAddingItem) {
                binding.apply {
                    productAmountEdit.visibility = View.INVISIBLE
                    productAmountEdit.text = Editable.Factory().newEditable("")
                    productNameEdit.visibility = View.INVISIBLE
                    productNameEdit.text = Editable.Factory().newEditable("")
                    addItemButton.setImageDrawable(
                        resources.getDrawable(
                            R.drawable.ic_baseline_add_24,
                            activity?.theme
                        )
                    )
                    addItemButton.setOnClickListener { homeViewModel?.startAddingItem() }

                    // Hide keyboard
                    view?.let {
                        val imm = getSystemService(requireContext(), InputMethodManager::class.java)
                        imm?.hideSoftInputFromWindow(it.windowToken, 0)
                    }
                }

            } else {
                binding.apply {
                    productAmountEdit.visibility = View.VISIBLE
                    productNameEdit.visibility = View.VISIBLE
                    productAmountEdit.requestFocus()
                    productAmountEdit.requestKeyboard()

                    addItemButton.setImageDrawable(
                        resources.getDrawable(
                            R.drawable.ic_baseline_done_24,
                            activity?.theme
                        )
                    )
                    addItemButton.setOnClickListener {
                        if (amountIsValid() && nameIsValid()) {
                            homeViewModel!!.doneAddingItem(
                                productNameEdit.text.toString(),
                                productAmountEdit.text.toString().toInt()
                            )
                        } else {
                            showErrorMessage()
                        }
                    }
                }
            }
        }

        return binding.root
    }

    private fun amountIsValid() = !binding.productAmountEdit.text.isNullOrBlank()

    private fun nameIsValid() = !binding.productNameEdit.text.isNullOrBlank()

    private fun showErrorMessage() {
        Snackbar.make(
            requireView(),
            R.string.invalid_input_error,
            Snackbar.LENGTH_LONG
        ).show()
    }
}