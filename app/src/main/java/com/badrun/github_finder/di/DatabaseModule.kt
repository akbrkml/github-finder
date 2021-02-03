package com.badrun.github_finder.di

import android.content.Context
import androidx.room.Room
import com.badrun.github_finder.data.source.local.dao.GithubUserDao
import com.badrun.github_finder.data.source.local.room.GithubDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): GithubDatabase = Room.databaseBuilder(
        context,
        GithubDatabase::class.java, "Github.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideGithubUserDao(database: GithubDatabase): GithubUserDao = database.githubUserDao()

}