package com.nfinnovagithub.features.repositoryList.data.di

import com.nfinnovagithub.features.repositoryList.data.RepositoryListApi
import com.nfinnovagithub.features.repositoryList.data.RepositoryListRemoteSourceImpl
import com.nfinnovagithub.features.repositoryList.data.RepositoryListRepositoryImpl
import com.nfinnovagithub.features.repositoryList.domain.RepositoryListRemoteSource
import com.nfinnovagithub.features.repositoryList.domain.RepositoryListRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

val repositoryList = module {
    singleOf(::RepositoryListRemoteSourceImpl).bind<RepositoryListRemoteSource>()
    singleOf(::RepositoryListRepositoryImpl).bind<RepositoryListRepository>()

    single<RepositoryListApi> {
        get<Retrofit>().create(RepositoryListApi::class.java)
    }
}