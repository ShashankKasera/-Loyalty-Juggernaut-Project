package com.example.loyaltyjuggernautproject.core

import android.view.View
import androidx.databinding.BindingAdapter

val String.Companion.EMPTY: String get() = ""

@BindingAdapter("visibleOrGone")
fun View.bindVisibility(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}