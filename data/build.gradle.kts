plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
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
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
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

kapt {
    correctErrorTypes = true
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

    testImplementation(Libs.Test.junit)
    androidTestImplementation(Libs.Test.junit)
    androidTestImplementation(Libs.Test.composeUiTestJunit4)

    debugImplementation(Libs.Debug.composeUiTooling)
    debugImplementation(Libs.Debug.composeUiTestManifest)

    // For apps targeting Android 12, add WorkManager dependency.
    constraints {
        implementation("androidx.work:work-runtime:${Versions.workRuntime}") {
            because("androidx.work:work-runtime:${Versions.workRuntime} pulled from play-services-ads has a bug ...")
        }
    }

}