package com.example.domain.provider

import kotlinx.coroutines.CoroutineDispatcher

interface DispatchersProvider {

    val Main: CoroutineDispatcher
    val IO: CoroutineDispatcher
    val Default: CoroutineDispatcher
    val Unconfined: CoroutineDispatcher

}