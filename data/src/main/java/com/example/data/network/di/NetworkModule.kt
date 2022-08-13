package com.example.data.network.di

import com.example.data.network.service.auth.AuthService
import com.example.data.network.service.auth.FakeAuthServiceImpl
import com.example.data.network.service.userdetails.FakeUserDetailsService
import com.example.data.network.service.userdetails.UserDetailsService
import org.koin.dsl.module

val networkModule = module {

    single<AuthService> {
        FakeAuthServiceImpl()
    }

    single<UserDetailsService> {
        FakeUserDetailsService()
    }

}