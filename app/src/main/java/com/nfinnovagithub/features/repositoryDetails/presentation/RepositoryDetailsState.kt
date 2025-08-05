package com.nfinnovagithub.features.repositoryDetails.presentation

import com.nfinnovagithub.features.repositoryDetails.domain.RepositoryDetails

sealed interface RepositoryDetailsState {
    data object Loading : RepositoryDetailsState
    data class Error(val error: String?) : RepositoryDetailsState
    data class Success(val repos: RepositoryDetails) : RepositoryDetailsState
}