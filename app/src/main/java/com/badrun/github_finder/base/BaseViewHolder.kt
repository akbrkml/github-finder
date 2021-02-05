package com.badrun.github_finder.base

import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

open class BaseViewHolder<T>(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    fun getString(@StringRes res: Int): String = itemView.context.getString(res)

    open fun bind(item: T) {}

}
