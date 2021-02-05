package com.badrun.github_finder.data.source.remote

import android.util.Log
import com.badrun.github_finder.data.source.remote.network.ApiResponse
import com.badrun.github_finder.data.source.remote.network.ApiService
import com.badrun.github_finder.data.source.remote.response.ErrorResponse
import com.badrun.github_finder.data.source.remote.response.GithubUserResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun searchUsers(query: Map<String, String>): Flow<ApiResponse<List<GithubUserResponse>>> {
        return flow {
            try {
                val response = apiService.searchUsers(query)
                when(response.code()) {
                    200 -> {
                        val dataArray = response.body()?.items
                        if (dataArray != null && dataArray.isNotEmpty()) {
                            emit(ApiResponse.Success(dataArray))
                        } else {
                            emit(ApiResponse.Empty)
                        }
                    }
                    else -> {
                        val errorResponse = convertErrorBody(response.errorBody())
                        emit(ApiResponse.Error(errorResponse?.message.toString()))
                    }

                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    private fun convertErrorBody(errorBody: ResponseBody?): ErrorResponse? {
        return try {
            errorBody?.charStream()?.let {
                Gson().fromJson(it, ErrorResponse::class.java)
            }
        } catch (exception: Exception) {
            null
        }
    }

}

