package com.badrun.github_finder.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.badrun.github_finder.base.BaseRecyclerViewAdapter
import com.badrun.github_finder.databinding.ItemGithubUserBinding
import com.badrun.github_finder.domain.model.GithubUser
import com.badrun.github_finder.utils.toDp

class MainAdapter : BaseRecyclerViewAdapter<GithubUser>() {

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemGithubUserBinding.inflate(layoutInflater, parent, false)
        return MainViewHolder(binding)
    }

    inner class MainViewHolder(private val binding: ItemGithubUserBinding) :
        RecyclerView.ViewHolder(binding.root), Binder<GithubUser> {

        override fun bind(item: GithubUser) {
            binding.textGithubUser.text = item.login
            binding.imageGithubUser.load(item.avatarUrl) {
                transformations(CircleCropTransformation())
            }
        }

        private fun setMargin() {
            val params = binding.container.layoutParams as ConstraintLayout.LayoutParams
            params.topMargin = if (adapterPosition == 0) 20f.toDp(binding.container.context)
            else 10f.toDp(binding.container.context)
            params.leftMargin = 20f.toDp(binding.container.context)
            params.rightMargin = 20f.toDp(binding.container.context)
            params.bottomMargin = 10f.toDp(binding.container.context)
            binding.container.layoutParams = params
        }

    }

}