package com.badrun.github_finder.data.source.local

import com.badrun.github_finder.data.source.local.dao.GithubUserDao
import com.badrun.github_finder.data.source.local.entity.GithubUserEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val githubUserDao: GithubUserDao) {

    fun searchUsers(username: String): Flow<List<GithubUserEntity>> = githubUserDao.getListByUsername(username)

    suspend fun insertGithubUser(githubUserList: List<GithubUserEntity>) = githubUserDao.insert(githubUserList)

}