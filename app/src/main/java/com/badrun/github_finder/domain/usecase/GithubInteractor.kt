package com.badrun.github_finder.domain.usecase

import com.badrun.github_finder.data.Resource
import com.badrun.github_finder.data.source.remote.network.QUERY_PAGE
import com.badrun.github_finder.data.source.remote.network.QUERY_PER_PAGE
import com.badrun.github_finder.data.source.remote.network.QUERY_SEARCH
import com.badrun.github_finder.domain.model.GithubUser
import com.badrun.github_finder.domain.repository.IGithubRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GithubInteractor @Inject constructor(private val githubRepository: IGithubRepository) : GithubUseCase {

    override fun searchUsers(keyword: String, page: Int): Flow<Resource<List<GithubUser>>> {
        val queryMap: Map<String, String> = mapOf (
            QUERY_SEARCH to keyword,
            QUERY_PAGE to page.toString(),
            QUERY_PER_PAGE to 10.toString()
        )
        return githubRepository.searchUsers(queryMap)
    }


}