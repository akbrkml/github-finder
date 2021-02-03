package com.badrun.github_finder.data.source.local.dao

import androidx.room.*
import com.badrun.github_finder.data.source.local.entity.GithubUserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GithubUserDao {

    @Query("SELECT * FROM github_user")
    fun getAll(): Flow<List<GithubUserEntity>>

    @Query("SELECT * FROM github_user WHERE login = :username")
    fun getByUsername(username: String): Flow<GithubUserEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(githubUserList: List<GithubUserEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(githubUser: GithubUserEntity)

}