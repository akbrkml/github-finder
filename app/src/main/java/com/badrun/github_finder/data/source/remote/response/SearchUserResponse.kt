package com.badrun.github_finder.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class SearchUserResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("items")
    val items: List<GithubUserResponse>
)