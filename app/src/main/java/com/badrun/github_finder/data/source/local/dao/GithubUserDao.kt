package com.badrun.github_finder.data.source.local.dao

import androidx.room.*
import com.badrun.github_finder.data.source.local.entity.GithubUserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GithubUserDao {

    @Query("SELECT * FROM github_user WHERE login LIKE :username GROUP BY id")
    fun getListByUsername(username: String): Flow<List<GithubUserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(githubUserList: List<GithubUserEntity>)

}