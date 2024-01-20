package com.appcessible.boardthebus.adapters

import android.util.TypedValue
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.appcessible.boardthebus.R
import com.appcessible.boardthebus.model.BusLoad


@BindingAdapter("checked")
fun ImageButton.setChecked(isChecked: Boolean) {
    isSelected = isChecked
}

@BindingAdapter("visible")
fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("estimatedArrBackground")
fun TextView.estimatedArrBg(busLoad: String) {
    val drw = when (BusLoad.getByString(busLoad)) {
        BusLoad.SEATS_AVAILABLE -> R.drawable.seats_available_text_bg
        BusLoad.STANDING_AVAILABLE -> R.drawable.standing_available_text_bg
        BusLoad.LIMITED_STANDING_AVAILABLE -> R.drawable.limited_standing_text_bg
        else -> R.color.colorSecondary
    }
    background = ContextCompat.getDrawable(context, drw)
}

