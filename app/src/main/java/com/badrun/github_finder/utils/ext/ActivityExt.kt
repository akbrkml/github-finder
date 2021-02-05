package com.badrun.github_finder.utils.ext

import android.app.Activity
import android.view.inputmethod.InputMethodManager

fun Activity.hideSoftKeyboard() {
    val inputMethodManager =
        this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
}
