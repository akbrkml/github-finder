package com.badrun.github_finder.data.source.remote

import android.util.Log
import com.badrun.github_finder.data.source.remote.network.ApiResponse
import com.badrun.github_finder.data.source.remote.network.ApiService
import com.badrun.github_finder.data.source.remote.response.GithubUserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun searchUsers(query: Map<String, String>): Flow<ApiResponse<List<GithubUserResponse>>> {
        return flow {
            try {
                val response = apiService.searchUsers(query)
                val dataArray = response.body()?.items
                if (dataArray != null && dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUserByUsername(username: String): Flow<ApiResponse<GithubUserResponse>> {
        return flow {
            try {
                val response = apiService.getUserByUsername(username)
                val dataArray = response.body()
                if (dataArray != null) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

}

