package com.nfinnovagithub.features.repositoryDetails.data

import com.nfinnovagithub.features.repositoryDetails.domain.RepositoryDetails
import com.nfinnovagithub.features.repositoryDetails.domain.RepositoryDetailsRemoteSource
import com.nfinnovagithub.features.repositoryDetails.domain.RepositoryDetailsRepository

class RepositoryDetailsRepositoryImpl(
    private val repositoryDetailsRemoteSource: RepositoryDetailsRemoteSource
    // Improvement - here add locale source (Room or other) for caching
) : RepositoryDetailsRepository {

    override suspend fun getDetailsAndTags(
        owner: String,
        repo: String
    ): Result<RepositoryDetails> {
        // Improvement - here first data will be loaded from locale source in parallel from api
        return repositoryDetailsRemoteSource.getDetailsAndTags(owner, repo)
    }
}