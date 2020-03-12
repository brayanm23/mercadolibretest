package com.test.mercadolibre.base.extensions

import androidx.navigation.fragment.NavHostFragment

/**
 * How to use this?
 *
 * Have your activity implement CustomizableBackActivity and
 * override onBackPressed.
 * To implement CustomizableBackActivity you need to do something like:
override fun getNavHostFragment(): NavHostFragment = <your layout fragment> as NavHostFragment
 * Then, to override onBackPressed use this:
override fun onBackPressed() {
if (!onBackPressedCustom()) {
super.onBackPressed()
}
}
 *
 * In your Fragment that needs a custom back behaviour,
 * implement CustomizableBackFragment and that's it!
 */

interface CustomizableBackFragment {
    fun onBackPressed()
}

interface CustomizableBackActivity {
    fun getNavHostFragment(): NavHostFragment
}

fun <T> T.onBackPressedCustom(): Boolean where T : CustomizableBackActivity {
    val fragmentsInStack = getNavHostFragment().childFragmentManager.fragments
    val lastFragment = fragmentsInStack.lastOrNull()
    if (lastFragment != null && lastFragment is CustomizableBackFragment) {
        lastFragment.onBackPressed()
        return true
    }
    return false
}