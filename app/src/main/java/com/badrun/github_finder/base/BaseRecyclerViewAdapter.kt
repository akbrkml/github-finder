package com.badrun.github_finder.base

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<T : Any> : ListAdapter<T, RecyclerView.ViewHolder>(BaseItemCallback<T>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        getViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position in currentList.indices) {
            (holder as Binder<T>).bind(currentList[position])
        }
    }

    abstract fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    interface Binder<in T> {
        fun bind(item: T)
    }

}