package com.badrun.github_finder.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import com.badrun.github_finder.base.BaseRecyclerViewAdapter
import com.badrun.github_finder.databinding.ItemGithubUserBinding
import com.badrun.github_finder.domain.model.GithubUser

class MainAdapter : BaseRecyclerViewAdapter<MainViewHolder>() {

    private val items = mutableListOf<GithubUser>()

    fun setData(items: List<GithubUser>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun clear() {
        this.items.clear()
        notifyDataSetChanged()
    }

    override fun getViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemGithubUserBinding.inflate(layoutInflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

}