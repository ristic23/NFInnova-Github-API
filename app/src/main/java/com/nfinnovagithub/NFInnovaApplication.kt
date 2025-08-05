package com.nfinnovagithub

import android.app.Application
import com.nfinnovagithub.di.networkModule
import com.nfinnovagithub.features.repositoryDetails.data.di.repositoryDetailsModule
import com.nfinnovagithub.features.repositoryDetails.presentation.di.repositoryViewModelModule
import com.nfinnovagithub.features.repositoryList.data.di.repositoryList
import com.nfinnovagithub.features.repositoryList.presentation.di.repositoryListViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NFInnovaApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@NFInnovaApplication)
            modules(
                networkModule,
                repositoryList,
                repositoryListViewModelModule,
                repositoryViewModelModule,
                repositoryDetailsModule,
            )
        }
    }
}