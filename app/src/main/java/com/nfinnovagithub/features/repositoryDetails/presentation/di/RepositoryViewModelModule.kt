package com.nfinnovagithub.features.repositoryDetails.presentation.di

import com.nfinnovagithub.features.repositoryDetails.presentation.RepositoryDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryViewModelModule = module {
    viewModel { RepositoryDetailsViewModel(get()) }
}