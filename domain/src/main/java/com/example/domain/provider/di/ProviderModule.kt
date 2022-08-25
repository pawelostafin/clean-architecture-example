package com.example.domain.provider.di

import com.example.domain.provider.DispatchersProvider
import com.example.domain.provider.DispatchersProviderImpl
import org.koin.dsl.module

val providerModule = module {

    single<DispatchersProvider> {
        DispatchersProviderImpl()
    }

}