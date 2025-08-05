package com.nfinnovagithub.features.repositoryList.presentation.di

import com.nfinnovagithub.features.repositoryList.presentation.RepositoryListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryListViewModelModule = module {
    viewModel { RepositoryListViewModel(get()) }
}