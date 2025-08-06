@file:OptIn(ExperimentalCoroutinesApi::class)

package com.nfinnovagithub.features.repositoryDetails.presentation

import app.cash.turbine.test
import com.nfinnovagithub.features.repositoryDetails.consts.repoDetails
import com.nfinnovagithub.features.repositoryDetails.domain.RepositoryDetailsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

internal class RepositoryDetailsViewModelTest {

    private val repository: RepositoryDetailsRepository = mockk(relaxed = true)
    private lateinit var viewModel: RepositoryDetailsViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = RepositoryDetailsViewModel(repository)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchRepos emits Loading then Success`() = runTest {
        val repoDetails = repoDetails()
        val owner = repoDetails.userName
        val repoName = repoDetails.repoName

        coEvery { repository.getDetailsAndTags(owner, repoName) } returns Result.success(repoDetails)

        viewModel.repoDetailsScreenState.test {
            val emission1 = awaitItem()
            assertEquals(emission1, RepositoryDetailsState.Loading)

            viewModel.fetch(owner, repoName)
            val emission2 = awaitItem()
            assertEquals(emission2, RepositoryDetailsState.Success(repoDetails))
        }
    }

    @Test
    fun `fetchRepos emits Loading then Error`() = runTest {
        val repoDetails = repoDetails()
        val owner = repoDetails.userName
        val repoName = repoDetails.repoName

        val errorMsg = "Network error"
        coEvery { repository.getDetailsAndTags(owner, repoName) } returns Result.failure(RuntimeException(errorMsg))

        viewModel.repoDetailsScreenState.test {
            val emission1 = awaitItem()
            assertEquals(emission1, RepositoryDetailsState.Loading)

            viewModel.fetch(owner, repoName)
            val emission2 = awaitItem()
            assertEquals(emission2, RepositoryDetailsState.Error(errorMsg))
        }
    }


}