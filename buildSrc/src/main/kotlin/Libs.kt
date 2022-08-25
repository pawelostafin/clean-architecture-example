object Libs {

    const val desugar = "com.android.tools:desugar_jdk_libs:" + Versions.desugar

    const val composeUi = "androidx.compose.ui:ui:" + Versions.compose
    const val composeMaterial = "androidx.compose.material:material:" + Versions.compose
    const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview:" + Versions.compose
    const val activityCompose = "androidx.activity:activity-compose:" + Versions.activityCompose
    const val coilCompose = "io.coil-kt:coil-compose:" + Versions.coilCompose
    const val constraintLayoutCompose = "androidx.constraintlayout:constraintlayout-compose:" + Versions.constraintLayoutCompose

    const val material = "com.google.android.material:material:" + Versions.material
    const val appCompat = "androidx.appcompat:appcompat:" + Versions.appcompat
    const val fragmentKtx = "androidx.fragment:fragment-ktx:" + Versions.fragment
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:" + Versions.constraintLayout
    const val coreKtx = "androidx.core:core-ktx:" + Versions.coreKtx
    const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:" + Versions.lifecycle
    const val lifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:" + Versions.lifecycle
    const val preference = "androidx.preference:preference:" + Versions.preference

    const val firebaseBom = "com.google.firebase:firebase-bom:" + Versions.firebaseBom
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx"
    const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics-ktx"

    const val retrofit = "com.squareup.retrofit2:retrofit:" + Versions.retrofit
    const val retrofitConverterMoshi = "com.squareup.retrofit2:converter-moshi:" + Versions.retrofit
    const val okhttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:" + Versions.okhttp
    const val okhttp = "com.squareup.okhttp3:okhttp:" + Versions.okhttp

    const val moshi = "com.squareup.moshi:moshi:" + Versions.moshi
    const val moshiCodegen = "com.squareup.moshi:moshi-kotlin-codegen:" + Versions.moshi

    const val timber = "com.jakewharton.timber:timber:" + Versions.timber

    const val koin = "io.insert-koin:koin-android:" + Versions.koin

    const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:" + Versions.navigation
    const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:" + Versions.navigation

    object Test {
        const val mockk = "io.mockk:mockk:" + Versions.mockk
        const val mockkAgentJvm = "io.mockk:mockk-agent-jvm:" + Versions.mockk
        const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:" + Versions.coroutinesTest
        const val turbine = "app.cash.turbine:turbine:" + Versions.turbine
        const val junit = "junit:junit:" + Versions.junit
        const val composeUiTestJunit4 = "androidx.compose.ui:ui-test-junit4:" + Versions.compose
        const val koinTest = "io.insert-koin:koin-test:" + Versions.koin
        const val koinTestJunit4 = "io.insert-koin:koin-test-junit4:" + Versions.koin
    }

    object Debug {
        const val composeUiTooling = "androidx.compose.ui:ui-tooling:" + Versions.compose
        const val composeUiTestManifest = "androidx.compose.ui:ui-test-manifest:" + Versions.compose
    }

}