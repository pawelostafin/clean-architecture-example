package com.example.data.network.di

import com.example.data.network.service.auth.AuthService
import com.example.data.network.service.auth.FakeAuthServiceImpl
import org.koin.dsl.module

val networkModule = module {

    factory<AuthService> {
        FakeAuthServiceImpl()
    }

}