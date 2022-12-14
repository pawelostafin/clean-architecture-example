plugins {
    id("com.android.library")
    kotlin("android")
    id("com.google.devtools.ksp")
}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk

        testInstrumentationRunner = AppConfig.androidTestInstrumentation
        consumerProguardFiles(AppConfig.proguardConsumerRules)
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = AppConfig.compileJavaVersion
        targetCompatibility = AppConfig.compileJavaVersion
    }

    kotlinOptions {
        jvmTarget = AppConfig.jvmTarget
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "Versions", "include" to listOf("*.jar"))))
    implementation(project(":domain"))

    coreLibraryDesugaring(Libs.desugar)

    implementation(Libs.preference)

    implementation(Libs.timber)

    implementation(Libs.koin)

    testImplementation(Libs.Test.koinTest)
    testImplementation(Libs.Test.koinTestJunit4)

    testImplementation(Libs.Test.mockk)
    testImplementation(Libs.Test.mockkAgentJvm)
    testImplementation(Libs.Test.coroutinesTest)
    testImplementation(Libs.Test.turbine)

    implementation(Libs.retrofit)
    implementation(Libs.retrofitConverterMoshi)
    implementation(Libs.okhttpLoggingInterceptor)
    implementation(Libs.okhttp)

    implementation(Libs.moshi)
    ksp(Libs.moshiCodegen)

    testImplementation(Libs.Test.junit)
    androidTestImplementation(Libs.Test.junit)
    androidTestImplementation(Libs.Test.composeUiTestJunit4)

    debugImplementation(Libs.Debug.composeUiTooling)
    debugImplementation(Libs.Debug.composeUiTestManifest)
}