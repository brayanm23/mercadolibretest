package com.test.mercadolibre.view

import android.os.Bundle
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.test.mercadolibre.R
import com.test.mercadolibre.base.extensions.afterTextChanged
import com.test.mercadolibre.base.extensions.closeKeyboard
import com.test.mercadolibre.base.extensions.getErrorMessageWithIcon
import com.test.mercadolibre.base.extensions.openKeyboard
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_search_product.*
import kotlinx.android.synthetic.main.fragment_search_product.backButton

class SearchFragment: DaggerFragment() {

    companion object {
        const val SEARCH_PRODUCT = "search_product_key"
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onResume() {
        super.onResume()
        searchInput.post {
            searchInput.requestFocus()
            openKeyboard()
        }
    }

    override fun onPause() {
        super.onPause()
        closeKeyboard()
    }

    private fun initViews() {
        searchInput.afterTextChanged(::validateIsNotEmpty)
        backButton.setOnClickListener { findNavController().navigateUp() }
        nextButton.setOnClickListener {
            if (searchInput.text.toString().isNotEmpty()){
                val args = Bundle()
                args.putString(SEARCH_PRODUCT, searchInput.text.toString())
                findNavController().navigate(
                    R.id.action_searchFragment_to_productListFragment,
                    args
                )
            } else {
                showError(true)
            }
        }
    }

    private fun validateIsNotEmpty(chars: CharSequence){
        if(chars.toString().isNotEmpty() && chars.toString().isNotBlank()){
            showError(false)
        } else {
            showError(true)
        }
    }

    private fun showError(show: Boolean) {
        if(show){
            searchInputLayout.error = getErrorMessageWithIcon(constraintLayout.context, R.string.search_input_is_empty)
        } else {
            searchInputLayout.error = null
        }
    }
}