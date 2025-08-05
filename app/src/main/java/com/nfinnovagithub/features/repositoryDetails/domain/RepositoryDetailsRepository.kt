package com.nfinnovagithub.features.repositoryDetails.domain

interface RepositoryDetailsRepository {
    suspend fun getDetailsAndTags(owner: String, repo: String): Result<RepositoryDetails>
}