package com.test.mercadolibre.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.test.mercadolibre.R
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_unespected_error.*

class UnespectedErrorFragment: DaggerFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_unespected_error, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        closeButton.setOnClickListener {
            goToInit()
        }

        errorView.setOnMainActionListener {
            goToInit()
        }
    }

    fun goToInit(){
        findNavController().navigate(R.id.action_unespectedErrorFragment_to_searchFragment)
    }

}