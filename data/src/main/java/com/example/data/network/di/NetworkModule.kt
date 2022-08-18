package com.example.data.network.di

import com.example.data.BuildConfig
import com.example.data.network.service.MarketService
import com.example.data.network.service.auth.AuthService
import com.example.data.network.service.auth.FakeAuthServiceImpl
import com.example.data.network.service.userdetails.FakeUserDetailsService
import com.example.data.network.service.userdetails.UserDetailsService
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.math.BigDecimal
import java.time.Duration
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

val networkModule = module {

    single<AuthService> {
        FakeAuthServiceImpl()
    }

    single<UserDetailsService> {
        FakeUserDetailsService()
    }

    single<OkHttpClient> {
        val timeoutDuration = Duration.ofSeconds(30)
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .apply {
                writeTimeout(timeoutDuration)
                readTimeout(timeoutDuration)
                callTimeout(timeoutDuration)
                connectTimeout(timeoutDuration)

                if (BuildConfig.DEBUG) {
                    addInterceptor(httpLoggingInterceptor)
                }
            }
            .build()
    }

    single {
        Moshi.Builder()
            .add(ZonedDateTimeAdapter)
            .add(BigDecimalAdapter)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.coingecko.com/api/v3/")
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }

    single {
        get<Retrofit>().createService<MarketService>()
    }

}

private inline fun <reified T> Retrofit.createService(): T {
    return create(T::class.java)
}

object BigDecimalAdapter {

    @FromJson
    fun fromJson(string: String) = BigDecimal(string)

    @ToJson
    fun toJson(value: BigDecimal) = value.toPlainString()

}

object ZonedDateTimeAdapter {

    @FromJson
    fun fromJson(string: String): ZonedDateTime {
        return ZonedDateTime
            .parse(string, DateTimeFormatter.ISO_ZONED_DATE_TIME)
            .withZoneSameInstant(ZoneOffset.UTC)
    }

    @ToJson
    fun toJson(zonedDateTime: ZonedDateTime): String {
        return zonedDateTime
            .withZoneSameInstant(ZoneOffset.UTC)
            .format(DateTimeFormatter.ISO_ZONED_DATE_TIME)
    }

}