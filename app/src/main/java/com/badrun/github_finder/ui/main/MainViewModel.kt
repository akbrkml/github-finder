package com.badrun.github_finder.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.badrun.github_finder.base.BaseViewModel
import com.badrun.github_finder.data.Resource
import com.badrun.github_finder.data.source.remote.network.ApiResponse
import com.badrun.github_finder.data.source.remote.response.GithubUserResponse
import com.badrun.github_finder.domain.model.GithubUser
import com.badrun.github_finder.domain.usecase.GithubUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(private val githubUseCase: GithubUseCase) :
    BaseViewModel() {

    private val _githubUserList = MutableLiveData<Resource<List<GithubUser>>>()
    val githubUserList: LiveData<Resource<List<GithubUser>>> get() = _githubUserList

    fun searchUsers(keyword: String, page: Int) =
        viewModelScope.launch(Dispatchers.Main) {
            githubUseCase.searchUsers(keyword, page).collect {
                _githubUserList.value = it
            }
        }

}