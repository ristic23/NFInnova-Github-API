package com.nfinnovagithub.features.repositoryDetails.data

import com.nfinnovagithub.features.repositoryDetails.RepositoryDetailsApi
import com.nfinnovagithub.features.repositoryDetails.data.mapper.toRepositoryDetails
import com.nfinnovagithub.features.repositoryDetails.data.mapper.toTag
import com.nfinnovagithub.features.repositoryDetails.domain.RepositoryDetails
import com.nfinnovagithub.features.repositoryDetails.domain.RepositoryDetailsRemoteSource
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.collections.map
import kotlin.coroutines.cancellation.CancellationException

class RepositoryDetailsRemoteSourceImpl(
    private val repositoryDetailsApi: RepositoryDetailsApi
) : RepositoryDetailsRemoteSource {

    override suspend fun getDetailsAndTags(owner: String, repo: String): Result<RepositoryDetails> {
        return runCatching {
            coroutineScope {
                val repoDeferred = async { repositoryDetailsApi.getRepository(owner, repo) }
                val tagsDeferred = async { repositoryDetailsApi.getRepositoryTags(owner, repo) }

                val repo = repoDeferred.await().toRepositoryDetails()
                val tags = tagsDeferred.await().map { dto -> dto.toTag() }
                repo.copy(tags = tags)
            }
        }.onFailure { e ->
            if (e is CancellationException) throw e
        }
    }
}