import org.gradle.api.JavaVersion

object AppConfig {

    const val minSdk = 24
    const val compileSdk = 33
    const val targetSdk = 33

    const val versionName = "1.0.0"
    const val versionCode = 1

    const val androidTestInstrumentation = "androidx.test.runner.AndroidJUnitRunner"
    const val proguardConsumerRules = "consumer-rules.pro"

    const val jvmTarget = "11"
    val compileJavaVersion = JavaVersion.VERSION_11

}