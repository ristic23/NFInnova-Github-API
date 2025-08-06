package com.nfinnovagithub.features.repositoryList.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nfinnovagithub.features.repositoryList.domain.RepositoryListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class RepositoryListViewModel(
    private val repository: RepositoryListRepository
): ViewModel() {

    private val _repoListScreenState: MutableStateFlow<RepositoryListState> = MutableStateFlow(RepositoryListState.Loading)
    val repoListScreenState: StateFlow<RepositoryListState> = _repoListScreenState.asStateFlow()


    fun fetchRepos(username: String) {
        viewModelScope.launch {
            _repoListScreenState.value = RepositoryListState.Loading
            try {
                val result = repository.getUserRepos(username).getOrThrow()
                _repoListScreenState.value = when {
                    result.isEmpty() -> RepositoryListState.Empty
                    else -> RepositoryListState.Success(result)
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _repoListScreenState.value = RepositoryListState.Error(e.message)
            }
        }
    }
}