package com.nfinnovagithub.features.repositoryList.data

import com.nfinnovagithub.features.repositoryList.data.dto.UserRepositoryDto
import com.nfinnovagithub.features.repositoryList.domain.RepositoryListRemoteSource
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.coroutines.cancellation.CancellationException
import kotlin.test.assertFailsWith

internal class RepositoryListRemoteSourceImplTest {

    private lateinit var remoteSource: RepositoryListRemoteSource
    private val repositoryListApi: RepositoryListApi = mockk(relaxed = true)

    private val username = "octocat"

    @Before
    fun setup() {
        remoteSource = RepositoryListRemoteSourceImpl(repositoryListApi)
    }

    @Test
    fun `getUserRepos returns mapped list on success`() = runTest {
        val dtoList = listOf(
            UserRepositoryDto(1, "repo1", 5),
            UserRepositoryDto(2, "repo2", 10)
        )
        coEvery { repositoryListApi.getUserRepos(username) } returns dtoList

        val result = remoteSource.getUserRepos(username)

        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrThrow().size)
    }

    @Test
    fun `getUserRepos returns failure on exception`() = runTest {
        val errorMsg = "API failure"
        coEvery { repositoryListApi.getUserRepos(username) } throws RuntimeException(errorMsg)

        val result = remoteSource.getUserRepos(username)

        assertTrue(result.isFailure)
        assertEquals(errorMsg, result.exceptionOrNull()?.message)
    }

    @Test
    fun `getUserRepos returns CancellationException`() = runTest {
        coEvery { repositoryListApi.getUserRepos(username) } throws CancellationException("Cancellation failure")

        assertFailsWith(CancellationException::class) {
            remoteSource.getUserRepos(username)
        }
    }
}