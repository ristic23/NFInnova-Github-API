package com.nfinnovagithub.features.repositoryDetails.data.di

import com.nfinnovagithub.features.repositoryDetails.RepositoryDetailsApi
import com.nfinnovagithub.features.repositoryDetails.data.RepositoryDetailsRemoteSourceImpl
import com.nfinnovagithub.features.repositoryDetails.data.RepositoryDetailsRepositoryImpl
import com.nfinnovagithub.features.repositoryDetails.domain.RepositoryDetailsRemoteSource
import com.nfinnovagithub.features.repositoryDetails.domain.RepositoryDetailsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

val repositoryDetailsModule = module {
    singleOf(::RepositoryDetailsRemoteSourceImpl).bind<RepositoryDetailsRemoteSource>()
    singleOf(::RepositoryDetailsRepositoryImpl).bind<RepositoryDetailsRepository>()
    single<RepositoryDetailsApi> {
        get<Retrofit>().create(RepositoryDetailsApi::class.java)
    }
}