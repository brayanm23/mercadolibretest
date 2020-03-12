package com.test.mercadolibre.base.widget.error

import android.content.Context
import android.text.SpannableString
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.test.mercadolibre.R
import kotlinx.android.synthetic.main.view_processing_error.view.*


class ProcessingErrorView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_processing_error, this, true)
        context.theme.obtainStyledAttributes(attrs, R.styleable.ProcessingError, 0, 0).apply {
            try {
                getString(R.styleable.ProcessingError_processingErrorTitle)?.let { title.text = it }
                getString(R.styleable.ProcessingError_processingErrorSubtitle)?.let { subtitle.text = it }
                getString(R.styleable.ProcessingError_processingErrorMainButton)?.let { mainButton.text = it }
                getString(R.styleable.ProcessingError_processingErrorSecondaryButton)?.let {
                    secondaryButton.text = it
                    secondaryButton.visibility = View.VISIBLE
                }
                val drawable = getDrawable(R.styleable.ProcessingError_processingErrorIcon)
                errorIcon.setImageDrawable(drawable)
            } finally {
                recycle()
            }
        }
    }

    fun setOnMainActionListener(listener: () -> Unit) {
        mainButton.setOnClickListener { listener() }
    }

    fun setOnSecondaryActionListener(listener: () -> Unit) {
        secondaryButton.setOnClickListener { listener() }
    }

    fun setTitle(text: String) {
        title.text = text
    }

    fun setSubTitle(text: String) {
        subtitle.text = text
    }

    fun setSubTitle(text: SpannableString) {
        subtitle.setText(text, TextView.BufferType.SPANNABLE)
    }

}