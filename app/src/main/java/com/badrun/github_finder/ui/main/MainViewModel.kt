package com.badrun.github_finder.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.asLiveData
import com.badrun.github_finder.base.BaseViewModel
import com.badrun.github_finder.domain.usecase.GithubUseCase

class MainViewModel @ViewModelInject constructor (private val githubUseCase: GithubUseCase) : BaseViewModel() {
    val githubUserList = githubUseCase.searchUsers("kamal", 0).asLiveData()
}