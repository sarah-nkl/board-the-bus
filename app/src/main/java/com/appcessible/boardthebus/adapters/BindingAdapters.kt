package com.appcessible.boardthebus.adapters

import android.widget.ImageButton
import androidx.databinding.BindingAdapter

@BindingAdapter("checked")
fun ImageButton.setChecked(isChecked: Boolean) {
    isSelected = isChecked
}
