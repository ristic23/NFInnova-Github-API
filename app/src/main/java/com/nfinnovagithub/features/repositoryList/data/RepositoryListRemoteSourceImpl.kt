package com.nfinnovagithub.features.repositoryList.data

import com.nfinnovagithub.features.repositoryList.data.mapper.toUserRepository
import com.nfinnovagithub.features.repositoryList.domain.RepositoryListRemoteSource
import com.nfinnovagithub.features.repositoryList.domain.UserRepository
import kotlin.coroutines.cancellation.CancellationException

class RepositoryListRemoteSourceImpl(
    private val repositoryListApi: RepositoryListApi
) : RepositoryListRemoteSource {

    override suspend fun getUserRepos(username: String): Result<List<UserRepository>> {
        return runCatching {
            val response = repositoryListApi.getUserRepos(username)
            val mappedList = response.map { dto -> dto.toUserRepository() }
            mappedList
        }.onFailure { e ->
            if (e is CancellationException) throw e
        }
    }

}