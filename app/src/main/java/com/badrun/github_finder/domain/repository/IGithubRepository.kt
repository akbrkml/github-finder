package com.badrun.github_finder.domain.repository

import com.badrun.github_finder.data.Resource
import com.badrun.github_finder.domain.model.GithubUser
import kotlinx.coroutines.flow.Flow

interface IGithubRepository {

    fun searchUsers(query: Map<String, String>): Flow<Resource<List<GithubUser>>>

}