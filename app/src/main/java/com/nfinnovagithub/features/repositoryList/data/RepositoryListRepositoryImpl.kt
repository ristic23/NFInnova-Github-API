package com.nfinnovagithub.features.repositoryList.data

import com.nfinnovagithub.features.repositoryList.domain.RepositoryListRemoteSource
import com.nfinnovagithub.features.repositoryList.domain.RepositoryListRepository
import com.nfinnovagithub.features.repositoryList.domain.UserRepository

class RepositoryListRepositoryImpl(
    private val repositoryListRemoteSource: RepositoryListRemoteSource,
    // Improvement - here add locale source (Room or other) for caching
): RepositoryListRepository {
    override suspend fun getUserRepos(username: String): Result<List<UserRepository>> {
        // Improvement - here first data will be loaded from locale source in parallel from api
        return repositoryListRemoteSource.getUserRepos(username)
    }
}