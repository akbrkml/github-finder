package com.badrun.github_finder.utils.mapper

import com.badrun.github_finder.data.source.local.entity.GithubUserEntity
import com.badrun.github_finder.data.source.remote.response.GithubUserResponse
import com.badrun.github_finder.domain.model.GithubUser

object GithubUserMapper {

    fun mapResponsesToEntities(input: List<GithubUserResponse>): List<GithubUserEntity> {
        val githubUserList = ArrayList<GithubUserEntity>()
        input.map {
            val githubUser = GithubUserEntity(
                id = it.id,
                avatarUrl = it.avatarUrl,
                followersUrl = it.followersUrl,
                followingUrl = it.followingUrl,
                gistsUrl = it.gistsUrl,
                login = it.login,
                organizationsUrl = it.organizationsUrl,
                reposUrl = it.reposUrl,
                starredUrl = it.starredUrl,
                subscriptionsUrl = it.subscriptionsUrl,
                url = it.url
            )
            githubUserList.add(githubUser)
        }
        return githubUserList
    }

    fun mapEntitiesToDomain(input: List<GithubUserEntity>): List<GithubUser> =
        input.map {
            GithubUser(
                id = it.id,
                avatarUrl = it.avatarUrl,
                followersUrl = it.followersUrl,
                followingUrl = it.followingUrl,
                gistsUrl = it.gistsUrl,
                login = it.login,
                organizationsUrl = it.organizationsUrl,
                reposUrl = it.reposUrl,
                starredUrl = it.starredUrl,
                subscriptionsUrl = it.subscriptionsUrl,
                url = it.url
            )
        }

}