package com.badrun.github_finder.ui.main

import coil.load
import coil.transform.CircleCropTransformation
import com.badrun.github_finder.base.BaseViewHolder
import com.badrun.github_finder.databinding.ItemGithubUserBinding
import com.badrun.github_finder.domain.model.GithubUser

class MainViewHolder(private val binding: ItemGithubUserBinding) : BaseViewHolder<GithubUser>(binding) {

    override fun bind(item: GithubUser) {
        binding.textGithubUser.text = item.login
        binding.imageGithubUser.load(item.avatarUrl) {
            transformations(CircleCropTransformation())
        }
    }
}