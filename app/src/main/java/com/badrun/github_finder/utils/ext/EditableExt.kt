package com.badrun.github_finder.utils.ext

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.setOnTextChanged(function: (s: CharSequence, start: Int, before: Int, count: Int) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            function(s, start, before, count)
        }

        override fun afterTextChanged(editable: Editable?) {}
    })
}