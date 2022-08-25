plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.devtools.ksp")
}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName
        testInstrumentationRunner = AppConfig.androidTestInstrumentation

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            keyAlias = "test123"
            keyPassword = "test123"
            storeFile = file("keys/test_key.jks")
            storePassword = "test123"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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

dependencies {
    implementation(fileTree(mapOf("dir" to "Versions", "include" to listOf("*.jar"))))
    implementation(project(":domain"))
    implementation(project(":data"))

    coreLibraryDesugaring(Libs.desugar)

    implementation(Libs.composeUi)
    implementation(Libs.composeMaterial)
    implementation(Libs.composeUiToolingPreview)
    implementation(Libs.activityCompose)
    implementation(Libs.coilCompose)
    implementation(Libs.constraintLayoutCompose)
    implementation(Libs.preference)

    implementation(Libs.material)
    implementation(Libs.appCompat)
    implementation(Libs.fragmentKtx)
    implementation(Libs.constraintLayout)
    implementation(Libs.coreKtx)
    implementation(Libs.lifecycleRuntimeKtx)
    implementation(Libs.lifecycleViewModelKtx)
    implementation("androidx.recyclerview:recyclerview:1.3.0-beta02")

    implementation(Libs.timber)

    implementation(platform(Libs.firebaseBom))
    implementation(Libs.firebaseAnalytics)
    implementation(Libs.firebaseCrashlytics)

    implementation(Libs.navigationFragmentKtx)
    implementation(Libs.navigationUiKtx)

    implementation(Libs.koin)

    testImplementation(Libs.Test.koinTest)
    testImplementation(Libs.Test.koinTestJunit4)

    testImplementation(Libs.Test.mockk)
    testImplementation(Libs.Test.mockkAgentJvm)
    testImplementation(Libs.Test.coroutinesTest)
    testImplementation(Libs.Test.turbine)

    testImplementation(Libs.Test.junit)
    androidTestImplementation(Libs.Test.junit)
    androidTestImplementation(Libs.Test.composeUiTestJunit4)

    debugImplementation(Libs.Debug.composeUiTooling)
    debugImplementation(Libs.Debug.composeUiTestManifest)
}
