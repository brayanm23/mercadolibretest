package com.test.mercadolibre.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.test.mercadolibre.R
import com.test.mercadolibre.base.extensions.loadImageURL
import com.test.mercadolibre.base.extensions.replaceHttpToHttps
import com.test.mercadolibre.model.Product
import com.test.mercadolibre.view.ProductListFragment.Companion.PRODUCT
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_detail_product.*
import kotlinx.android.synthetic.main.fragment_product_list.backButton

class DetailProductFragment: DaggerFragment() {

    companion object {
        const val YES = "Si"
        const val NO = "No"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }
        arguments?.let {
            val product = it.getSerializable(PRODUCT) as Product
            imageProduct.loadImageURL(product.thumbnail.replaceHttpToHttps())
            titleProduct.text = product.title
            condition.text = product.condition
            price.text = product.price.toString()
            acceptMercadopago.text = if (product.accepts_mercadopago) YES else NO
            quantity.text = product.available_quantity.toString()
        }
    }
}