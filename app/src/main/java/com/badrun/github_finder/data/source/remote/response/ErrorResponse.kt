package com.badrun.github_finder.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("documentation_url")
    val documentationUrl: String,
    @SerializedName("message")
    val message: String
)