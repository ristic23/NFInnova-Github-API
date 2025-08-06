package com.nfinnovagithub.features.repositoryDetails.data

import com.nfinnovagithub.features.repositoryDetails.RepositoryDetailsApi
import com.nfinnovagithub.features.repositoryDetails.consts.repoDto
import com.nfinnovagithub.features.repositoryDetails.consts.tagDto
import com.nfinnovagithub.features.repositoryDetails.domain.RepositoryDetailsRemoteSource
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.coroutines.cancellation.CancellationException
import kotlin.test.assertFailsWith

internal class RepositoryDetailsRemoteSourceImplTest {

    private lateinit var remoteSource: RepositoryDetailsRemoteSource
    private val repositoryDetailsApi: RepositoryDetailsApi = mockk(relaxed = true)

    @Before
    fun setup() {
        remoteSource = RepositoryDetailsRemoteSourceImpl(repositoryDetailsApi)
    }

    @Test
    fun `getDetailsAndTags returns Success when both API calls succeed`() = runTest {
        val repoDto = repoDto()
        val tagsDto = listOf(tagDto(), tagDto())
        val owner = repoDto.ownerDto.name
        val repoName = repoDto.repoName

        coEvery { repositoryDetailsApi.getRepository(owner, repoName) } returns repoDto
        coEvery { repositoryDetailsApi.getRepositoryTags(owner, repoName) } returns tagsDto

        val result = remoteSource.getDetailsAndTags(owner, repoName)

        assertTrue(result.isSuccess)
        val value = result.getOrNull()
        assertEquals("octocat", value?.userName)
        assertEquals("Hello-World", value?.repoName)
        assertEquals(2, value?.watchers)
        assertEquals(1, value?.forks)
        assertEquals(2, value?.tags?.size)
    }

    @Test
    fun `getDetailsAndTags returns Exception when repository API call failed`() = runTest {
        val repoDto = repoDto()
        val tagsDto = listOf(tagDto(), tagDto())
        val owner = repoDto.ownerDto.name
        val repoName = repoDto.repoName

        val errorRepoMsg = "Repos API failure"
        coEvery { repositoryDetailsApi.getRepository(owner, repoName) } throws RuntimeException(errorRepoMsg)
        coEvery { repositoryDetailsApi.getRepositoryTags(owner, repoName) } returns tagsDto

        val result = remoteSource.getDetailsAndTags(owner, repoName)

        assertTrue(result.isFailure)
        assertEquals(errorRepoMsg, result.exceptionOrNull()?.message)
    }

    @Test
    fun `getDetailsAndTags returns Exception when repo tags API call failed`() = runTest {
        val repoDto = repoDto()
        val owner = repoDto.ownerDto.name
        val repoName = repoDto.repoName

        val errorTagsMsg = "Tags API failure"
        coEvery { repositoryDetailsApi.getRepository(owner, repoName) } returns repoDto
        coEvery { repositoryDetailsApi.getRepositoryTags(owner, repoName) } throws RuntimeException(errorTagsMsg)

        val result = remoteSource.getDetailsAndTags(owner, repoName)

        assertTrue(result.isFailure)
        assertEquals(errorTagsMsg, result.exceptionOrNull()?.message)
    }

    @Test
    fun `getUserRepos returns CancellationException`() = runTest {
        val repoDto = repoDto()
        val owner = repoDto.ownerDto.name
        val repoName = repoDto.repoName

        val cancellationRepoMsg = "Cancellation Repository failure"
        val cancellationTagsMsg = "Cancellation Repo Tags failure"
        coEvery { repositoryDetailsApi.getRepository(owner, repoName) } throws CancellationException(cancellationRepoMsg)
        coEvery { repositoryDetailsApi.getRepositoryTags(owner, repoName) } throws CancellationException(cancellationTagsMsg)

        assertFailsWith(CancellationException::class) {
            remoteSource.getDetailsAndTags(owner, repoName)
        }
    }

}