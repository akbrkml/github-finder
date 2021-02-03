package com.badrun.github_finder.di

import com.badrun.github_finder.data.source.GithubRepository
import com.badrun.github_finder.domain.repository.IGithubRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(githubRepository: GithubRepository): IGithubRepository

}