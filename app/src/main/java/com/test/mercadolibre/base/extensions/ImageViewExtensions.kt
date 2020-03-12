package com.test.mercadolibre.base.extensions

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.test.mercadolibre.R
import com.squareup.picasso.Picasso

fun ImageView.loadImageURL(url: String, @DrawableRes error : Int  = R.drawable.error_load_img) {
    Picasso.with(context)
        .load(url)
        .error(error)
        .into(this)
}


