package com.badrun.github_finder.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.badrun.github_finder.base.BaseRecyclerViewAdapter
import com.badrun.github_finder.databinding.ItemGithubUserBinding
import com.badrun.github_finder.databinding.ItemProgressBinding
import com.badrun.github_finder.domain.model.GithubUser

class MainAdapter : BaseRecyclerViewAdapter<RecyclerView.ViewHolder>() {

    private var isLoading = false

    private val items = mutableListOf<GithubUser>()

    fun setData(items: List<GithubUser>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun clear() {
        this.items.clear()
        notifyDataSetChanged()
    }

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            GITHUB_DATA -> {
                val binding = ItemGithubUserBinding.inflate(layoutInflater, parent, false)
                MainViewHolder(binding)
            }
            else -> {
                val binding = ItemProgressBinding.inflate(layoutInflater, parent, false)
                LoadingHolder(binding)
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            GITHUB_DATA -> {
                val item = items[position]
                holder as MainViewHolder
                holder.bind(item)
            }
            else -> {
            }
        }

    }

    override fun getItemCount(): Int = items.size + if (isLoading) 1 else 0

    override fun getItemViewType(position: Int): Int {
        return if (isLoading && position == itemCount - 1) {
            LOADING
        } else {
            GITHUB_DATA
        }
    }

    fun hideLoading() {
        isLoading = false
        notifyItemRemoved(itemCount - 1)
    }

    fun showLoading() {
        isLoading = true
        notifyItemInserted(itemCount + 1)
    }

    companion object {
        const val GITHUB_DATA = 0
        const val LOADING = 1
    }

}