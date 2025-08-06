package com.nfinnovagithub.features.repositoryDetails.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nfinnovagithub.features.repositoryDetails.domain.RepositoryDetailsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class RepositoryDetailsViewModel(
    private val repositoryDetailsRepository: RepositoryDetailsRepository
): ViewModel() {

    private val _repoDetailsScreenState: MutableStateFlow<RepositoryDetailsState> = MutableStateFlow(RepositoryDetailsState.Loading)
    val repoDetailsScreenState: StateFlow<RepositoryDetailsState> = _repoDetailsScreenState.asStateFlow()


    fun fetch(owner: String, repo: String) {
        viewModelScope.launch {
            _repoDetailsScreenState.value = RepositoryDetailsState.Loading
            try {
                val result = repositoryDetailsRepository.getDetailsAndTags(owner, repo).getOrThrow()
                _repoDetailsScreenState.value = RepositoryDetailsState.Success(result)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _repoDetailsScreenState.value = RepositoryDetailsState.Error(e.message)
            }
        }
    }
}