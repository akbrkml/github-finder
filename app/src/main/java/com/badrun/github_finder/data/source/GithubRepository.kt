package com.badrun.github_finder.data.source

import com.badrun.github_finder.data.NetworkBoundResource
import com.badrun.github_finder.data.Resource
import com.badrun.github_finder.data.source.local.LocalDataSource
import com.badrun.github_finder.data.source.remote.RemoteDataSource
import com.badrun.github_finder.data.source.remote.network.ApiResponse
import com.badrun.github_finder.data.source.remote.network.QUERY_SEARCH
import com.badrun.github_finder.data.source.remote.response.GithubUserResponse
import com.badrun.github_finder.domain.model.GithubUser
import com.badrun.github_finder.domain.repository.IGithubRepository
import com.badrun.github_finder.utils.mapper.GithubUserMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IGithubRepository {

    override fun searchUsers(query: Map<String, String>): Flow<Resource<List<GithubUser>>> =
        object : NetworkBoundResource<List<GithubUser>, List<GithubUserResponse>>() {
            override fun loadFromDB(): Flow<List<GithubUser>> {
                val username = "%${query[QUERY_SEARCH]}%"
                return localDataSource.searchUsers(username).map { GithubUserMapper.mapEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<GithubUser>?): Boolean = true

            override suspend fun createCall(): Flow<ApiResponse<List<GithubUserResponse>>> {
                return remoteDataSource.searchUsers(query)
            }

            override suspend fun saveCallResult(data: List<GithubUserResponse>) {
                val githubUserEntities = GithubUserMapper.mapResponsesToEntities(data)
                localDataSource.insertGithubUser(githubUserEntities)
            }

        }.asFlow()

}