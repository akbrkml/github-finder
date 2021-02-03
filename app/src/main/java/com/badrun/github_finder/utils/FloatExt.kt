/*
 * Created by Daniel Partogi on 6/27/20 12:32 AM
 * Copyright (c) 2020.
 * Last Modified 6/27/20 12:32 AM
 */

package com.badrun.github_finder.utils

import android.content.Context
import android.util.TypedValue

fun Float.toDp(context: Context) :Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        context.resources.displayMetrics
    ).toInt()
}