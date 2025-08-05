package com.nfinnovagithub.features.repositoryList.presentation

import com.nfinnovagithub.features.repositoryList.domain.UserRepository

sealed interface RepositoryListState {
    data object Loading : RepositoryListState
    data class Error(val error: String?) : RepositoryListState
    data object Empty : RepositoryListState
    data class Success(val repos: List<UserRepository>) : RepositoryListState
}