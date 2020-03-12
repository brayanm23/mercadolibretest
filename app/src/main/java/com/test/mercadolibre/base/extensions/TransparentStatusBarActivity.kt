package com.test.mercadolibre.base.extensions

import android.os.Bundle
import android.view.View.*
import android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.test.mercadolibre.R


/*
    A [DaggerAppCompatActivity] that makes the status bar transparent and expands
    the content up to the top of the screen
*/

abstract class TransparentStatusBarActivity : AppCompatActivity() {

    var statusBarIsLight = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = ContextCompat.getColor(this, R.color.transparent)
        window.addFlags(FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        statusBarIsLight = window.decorView.systemUiVisibility includesFlag SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        window.decorView.setOnSystemUiVisibilityChangeListener {
            hideStatusBar()
        }
    }

    override fun onResume() {
        super.onResume()
        hideStatusBar()
    }

    fun showDarkIcons(makeDark: Boolean) {
        statusBarIsLight = makeDark
        showDarkIcons()
    }

    private fun hideStatusBar() {
        window.decorView.systemUiVisibility = (SYSTEM_UI_FLAG_LAYOUT_STABLE
                combinedWith SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        showDarkIcons()
    }

    private fun showDarkIcons() {
        if (statusBarIsLight) {
            window.decorView.systemUiVisibility =
                    window.decorView.systemUiVisibility combinedWith SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            window.decorView.systemUiVisibility =
                    window.decorView.systemUiVisibility removing SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

}
