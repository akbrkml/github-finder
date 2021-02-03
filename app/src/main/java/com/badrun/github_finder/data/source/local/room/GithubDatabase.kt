package com.badrun.github_finder.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.badrun.github_finder.data.source.local.dao.GithubUserDao
import com.badrun.github_finder.data.source.local.entity.GithubUserEntity

@Database(
    entities = [GithubUserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GithubDatabase : RoomDatabase() {

    abstract fun githubUserDao(): GithubUserDao

}