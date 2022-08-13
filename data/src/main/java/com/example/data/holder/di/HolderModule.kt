package com.example.data.holder.di

import com.example.data.holder.SessionDataHolder
import com.example.data.holder.SessionDataHolderImpl
import org.koin.dsl.module

val holderModule = module {

    single<SessionDataHolder> {
        SessionDataHolderImpl(
            sharedPreferences = get()
        )
    }

}