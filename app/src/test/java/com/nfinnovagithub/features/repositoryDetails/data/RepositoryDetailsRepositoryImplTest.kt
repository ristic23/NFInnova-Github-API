package com.nfinnovagithub.features.repositoryDetails.data

import com.nfinnovagithub.features.repositoryDetails.consts.repoDetails
import com.nfinnovagithub.features.repositoryDetails.domain.RepositoryDetailsRemoteSource
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.coroutines.cancellation.CancellationException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

internal class RepositoryDetailsRepositoryImplTest {
    private val remoteSource: RepositoryDetailsRemoteSource = mockk(relaxed = true)
    private lateinit var repository: RepositoryDetailsRepositoryImpl

    @Before
    fun setup() {
        repository = RepositoryDetailsRepositoryImpl(remoteSource)
    }

    @Test
    fun `getUserRepos delegates to remote source`() = runTest {
        val repoDetails = repoDetails()
        val owner = repoDetails.userName
        val repoName = repoDetails.repoName

        coEvery { remoteSource.getDetailsAndTags(owner, repoName) } returns Result.success(repoDetails)

        val result = repository.getDetailsAndTags(owner, repoName)

        assertTrue(result.isSuccess)
        assertEquals(repoDetails, result.getOrThrow())
    }

    @Test
    fun `getUserRepos returns CancellationException`() = runTest {
        val repoDetails = repoDetails()
        val owner = repoDetails.userName
        val repoName = repoDetails.repoName

        coEvery { remoteSource.getDetailsAndTags(owner, repoName) } throws CancellationException("Cancellation failure")

        assertFailsWith(CancellationException::class) {
            remoteSource.getDetailsAndTags(owner, repoName)
        }
    }
}