package com.nfinnovagithub.features.repositoryDetails.domain

interface RepositoryDetailsRemoteSource {

    suspend fun getDetailsAndTags(owner: String, repo: String): Result<RepositoryDetails>

}