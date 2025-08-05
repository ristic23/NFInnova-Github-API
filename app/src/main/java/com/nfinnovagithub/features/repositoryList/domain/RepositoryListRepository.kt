package com.nfinnovagithub.features.repositoryList.domain

interface RepositoryListRepository {

    suspend fun getUserRepos(username: String): Result<List<UserRepository>>

}