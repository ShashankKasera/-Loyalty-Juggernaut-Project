package com.example.loyaltyjuggernautproject.core

import android.view.View

val String.Companion.EMPTY: String get() = ""

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}