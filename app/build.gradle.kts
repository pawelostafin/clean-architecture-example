plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName
        testInstrumentationRunner = AppConfig.androidTestInstrumentation
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCompiler
    }

    buildFeatures {
        compose = true
    }

    viewBinding {
        android.buildFeatures.viewBinding = true
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = AppConfig.compileJavaVersion
        targetCompatibility = AppConfig.compileJavaVersion
    }

    kotlinOptions {
        jvmTarget = AppConfig.jvmTarget
    }

    packagingOptions {
        resources.excludes.add("META-INF/NOTICE.md")
        resources.excludes.add("META-INF/LICENSE.md")
        resources.excludes.add("META-INF/NOTICE.markdown")
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(fileTree(mapOf("dir" to "Versions", "include" to listOf("*.jar"))))
    implementation(project(":domain"))

    coreLibraryDesugaring(Libs.desugar)

    implementation(Libs.composeUi)
    implementation(Libs.composeMaterial)
    implementation(Libs.composeUiToolingPreview)
    implementation(Libs.activityCompose)

    implementation(Libs.material)
    implementation(Libs.appCompat)
    implementation(Libs.fragmentKtx)
    implementation(Libs.constraintLayout)
    implementation(Libs.coreKtx)
    implementation(Libs.lifecycleRuntimeKtx)
    implementation(Libs.lifecycleViewModelKtx)

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
