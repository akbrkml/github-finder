package com.badrun.github_finder.domain.model

data class GithubUser(
    val id: Int,
    val avatarUrl: String,
    val followersUrl: String,
    val followingUrl: String,
    val gistsUrl: String,
    val login: String,
    val organizationsUrl: String,
    val reposUrl: String,
    val starredUrl: String,
    val subscriptionsUrl: String,
    val url: String
)