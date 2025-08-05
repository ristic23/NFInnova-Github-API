package com.nfinnovagithub.features.repositoryList.domain

interface RepositoryListRemoteSource {

    suspend fun getUserRepos(username: String): Result<List<UserRepository>>

}