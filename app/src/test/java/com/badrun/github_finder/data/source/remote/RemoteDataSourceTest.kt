package com.badrun.github_finder.data.source.remote

import com.badrun.github_finder.data.source.remote.network.*
import com.badrun.github_finder.data.source.remote.response.ErrorResponse
import com.badrun.github_finder.data.source.remote.response.GithubUserResponse
import com.badrun.github_finder.data.source.remote.response.SearchUserResponse
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.stub
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.flow.collect
import okhttp3.ResponseBody.Companion.toResponseBody
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import retrofit2.Response

class RemoteDataSourceTest {

    private val apiService = mock<ApiService>()
    private val remoteDataSource = RemoteDataSource(apiService)

    private val queryMap: Map<String, String> = mapOf (
        QUERY_SEARCH to "akbrkml",
        QUERY_PAGE to 1.toString(),
        QUERY_PER_PAGE to 10.toString()
    )

    private val queryMapEmpty: Map<String, String> = mapOf (
        QUERY_SEARCH to "akbrkmlk",
        QUERY_PAGE to 1.toString(),
        QUERY_PER_PAGE to 10.toString()
    )

    private val queryMapError: Map<String, String> = mapOf (
        QUERY_SEARCH to "",
        QUERY_PAGE to 1.toString(),
        QUERY_PER_PAGE to 10.toString()
    )

    private val githubUserResponse = GithubUserResponse(
        login = "akbrkml",
        id = 19419964,
        nodeId = "MDQ6VXNlcjE5NDE5OTY0",
        avatarUrl = "https://avatars.githubusercontent.com/u/19419964?v=4",
        gravatarId = "",
        url = "https://api.github.com/users/akbrkml",
        htmlUrl = "https://github.com/akbrkml",
        followersUrl = "https://api.github.com/users/akbrkml/followers",
        followingUrl = "https://api.github.com/users/akbrkml/following{/other_user}",
        gistsUrl = "https://api.github.com/users/akbrkml/gists{/gist_id}",
        starredUrl = "https://api.github.com/users/akbrkml/starred{/owner}{/repo}",
        subscriptionsUrl = "https://api.github.com/users/akbrkml/subscriptions",
        organizationsUrl = "https://api.github.com/users/akbrkml/orgs",
        reposUrl = "https://api.github.com/users/akbrkml/repos",
        eventsUrl = "https://api.github.com/users/akbrkml/events{/privacy}",
        receivedEventsUrl = "https://api.github.com/users/akbrkml/received_events",
        type = "User",
        siteAdmin = false,
        score = 1.0
    )

    @Test
    fun `flow emits successfully`() = runBlocking {

        // Mock API Service
        val searchUserResponse = SearchUserResponse(
            totalCount = 1, incompleteResults = false, items = listOf(githubUserResponse)
        )
        val response: Response<SearchUserResponse> = Response.success(searchUserResponse)
        apiService.stub {
            onBlocking { searchUsers(queryMap) } doReturn response
        }
        // Test
        val flow = remoteDataSource.searchUsers(queryMap)

        // Verify
        flow.collect { result ->
            when (result) {
                is ApiResponse.Success -> {
                    result.data shouldBeEqualTo listOf(githubUserResponse)
                }
            }
        }

    }

    @Test
    fun `flow emits empty`() = runBlocking {

        // Mock API Service
        val searchUserResponse = SearchUserResponse(
            totalCount = 1, incompleteResults = false, items = listOf()
        )
        val response: Response<SearchUserResponse> = Response.success(searchUserResponse)
        apiService.stub {
            onBlocking { searchUsers(queryMapEmpty) } doReturn response
        }
        // Test
        val flow = remoteDataSource.searchUsers(queryMapEmpty)

        // Verify
        flow.collect { result ->
            when (result) {
                is ApiResponse.Empty -> {
                    result shouldBeEqualTo ApiResponse.Empty
                }
            }
        }

    }

    @Test
    fun `flow emits error`() = runBlocking {

        // Mock API Service
        val errorResponse = ErrorResponse(
            message = "Validation Failed", documentationUrl = "https://docs.github.com/v3/search"
        )

        val response: Response<SearchUserResponse> = Response.error(422, errorResponse.toString().toResponseBody())
        apiService.stub {
            onBlocking { searchUsers(queryMapError) } doReturn response
        }
        // Test
        val flow = remoteDataSource.searchUsers(queryMapError)

        // Verify
        flow.collect { result ->
            when (result) {
                is ApiResponse.Error -> {
                    result.errorMessage shouldBeEqualTo errorResponse.message
                }
            }
        }

    }

}