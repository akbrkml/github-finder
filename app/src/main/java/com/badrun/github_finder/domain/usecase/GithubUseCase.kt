package com.badrun.github_finder.domain.usecase

import com.badrun.github_finder.data.Resource
import com.badrun.github_finder.domain.model.GithubUser
import kotlinx.coroutines.flow.Flow

interface GithubUseCase {

    fun searchUsers(keyword: String, page: Int): Flow<Resource<List<GithubUser>>>

}