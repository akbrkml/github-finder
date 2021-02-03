package com.badrun.github_finder.di

import com.badrun.github_finder.domain.usecase.GithubInteractor
import com.badrun.github_finder.domain.usecase.GithubUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class AppModule {

    @Binds
    abstract fun provideGithubUseCase(githubInteractor: GithubInteractor): GithubUseCase

}