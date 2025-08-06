package com.nfinnovagithub.features.repositoryList.data

import com.nfinnovagithub.features.repositoryList.domain.RepositoryListRemoteSource
import com.nfinnovagithub.features.repositoryList.domain.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import org.junit.Before
import kotlin.test.Test
import kotlinx.coroutines.test.runTest
import kotlin.coroutines.cancellation.CancellationException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

internal class RepositoryListRepositoryImplTest {

    private val remoteSource: RepositoryListRemoteSource = mockk(relaxed = true)
    private lateinit var repository: RepositoryListRepositoryImpl
    private val username = "octocat"

    @Before
    fun setup() {
        repository = RepositoryListRepositoryImpl(remoteSource)
    }

    @Test
    fun `getUserRepos delegates to remote source`() = runTest {
        val expectedList = listOf(UserRepository(1, "repo1", 5))

        coEvery { remoteSource.getUserRepos(username) } returns Result.success(expectedList)

        val result = repository.getUserRepos(username)

        assertTrue(result.isSuccess)
        assertEquals(expectedList, result.getOrThrow())
    }

    @Test
    fun `getUserRepos returns CancellationException`() = runTest {
        coEvery { remoteSource.getUserRepos(username) } throws CancellationException("Cancellation failure")

        assertFailsWith(CancellationException::class) {
            remoteSource.getUserRepos(username)
        }
    }
}