package com.badrun.github_finder.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "github_user")
data class GithubUserEntity(
    @PrimaryKey
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