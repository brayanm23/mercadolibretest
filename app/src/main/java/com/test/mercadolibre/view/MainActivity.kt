package com.test.mercadolibre.view

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.test.mercadolibre.R
import com.test.mercadolibre.base.extensions.CustomizableBackActivity
import com.test.mercadolibre.base.extensions.TransparentStatusBarActivity
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : TransparentStatusBarActivity(), CustomizableBackActivity {

    override fun getNavHostFragment(): NavHostFragment = fragmentHostNav as NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}
