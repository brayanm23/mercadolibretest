package com.test.mercadolibre.base.extensions

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ImageSpan
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import com.test.mercadolibre.R

fun Fragment.closeKeyboard() {
    view?.let {
        val imm = it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

fun Fragment.openKeyboard() {
    view?.let {
        val imm = it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }
}

fun Fragment.getErrorMessageWithIcon(context: Context, stringResId: Int): SpannableString {

    val errorDrawable = AppCompatResources.getDrawable(context, R.drawable.ic_error_message)
        ?: ColorDrawable(Color.TRANSPARENT)
    errorDrawable.apply {
        setBounds(0, 0, intrinsicWidth, intrinsicHeight)
    }

    val errorString = "  " + getString(stringResId)

    return SpannableString(errorString).apply {
        setSpan(ImageSpan(errorDrawable, ImageSpan.ALIGN_BOTTOM),
            0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
    }
}
