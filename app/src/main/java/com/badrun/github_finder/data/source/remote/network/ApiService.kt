package com.badrun.github_finder.data.source.remote.network

import com.badrun.github_finder.data.source.remote.response.SearchUserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap


interface ApiService {

    @GET(ENDPOINT_SEARCH_USERS)
    suspend fun searchUsers(
        @QueryMap query: Map<String, String>
    ): Response<SearchUserResponse>

}
