@file:OptIn(ExperimentalCoroutinesApi::class)

package com.nfinnovagithub.features.repositoryList.presentation

import com.nfinnovagithub.features.repositoryList.domain.RepositoryListRepository
import com.nfinnovagithub.features.repositoryList.domain.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test
import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import kotlin.test.assertEquals

internal class RepositoryListViewModelTest {

    private val repository: RepositoryListRepository = mockk(relaxed = true)
    private lateinit var viewModel: RepositoryListViewModel
    private val testDispatcher = StandardTestDispatcher()
    private val username = "octocat"

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = RepositoryListViewModel(repository)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchRepos emits Loading then Success`() = runTest {
        val userRepos = listOf(UserRepository(1, "Repo1", 5))

        coEvery { repository.getUserRepos(username) } returns Result.success(userRepos)

        viewModel.repoListScreenState.test {
            val emission1 = awaitItem()
            assertEquals(emission1, RepositoryListState.Loading)

            viewModel.fetchRepos(username)
            val emission2 = awaitItem()
            assertEquals(emission2, RepositoryListState.Success(userRepos))
        }
    }

    @Test
    fun `fetchRepos emits Loading then Empty`() = runTest {
        coEvery { repository.getUserRepos(username) } returns Result.success(emptyList())

        viewModel.repoListScreenState.test {
            val emission1 = awaitItem()
            assertEquals(emission1, RepositoryListState.Loading)

            viewModel.fetchRepos(username)
            val emission2 = awaitItem()
            assertEquals(emission2, RepositoryListState.Empty)
        }
    }

    @Test
    fun `fetchRepos emits Loading then Error`() = runTest {
        val errorMsg = "network error"
        coEvery { repository.getUserRepos(username) } returns Result.failure(RuntimeException(errorMsg))

        viewModel.repoListScreenState.test {
            val emission1 = awaitItem()
            assertEquals(emission1, RepositoryListState.Loading)

            viewModel.fetchRepos(username)
            val emission2 = awaitItem()
            assertEquals(emission2, RepositoryListState.Error(errorMsg))
        }
    }


}