package com.appcessible.boardthebus.adapters

import android.view.View
import android.widget.ImageButton
import androidx.databinding.BindingAdapter

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
