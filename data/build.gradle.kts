import config.AndroidConfig
import dependencies.Dependencies
import flavor.BuildTypeDebug
import flavor.BuildTypeRelease

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
    id("kotlinx-serialization")
}

android {
    namespace = AndroidConfig.APP_ID + ".data"
    compileSdk = AndroidConfig.COMPILE_SDK_VERSION

    defaultConfig {
        minSdk = AndroidConfig.MIN_SDK_VERSION
        targetSdk = AndroidConfig.TARGET_SDK_VERSION

        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
        getByName("debug") {
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
            isJniDebuggable = BuildTypeDebug.isDebuggable
        }
    }
    compileOptions {
        sourceCompatibility = AndroidConfig.JAVA_VERSION
        targetCompatibility = AndroidConfig.JAVA_VERSION
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(mapOf("path" to ":domain")))

    // Core dependencies
    implementation(Dependencies.Core.KTX)
    implementation(Dependencies.Core.LIFECYCLE_RUNTIME)

    // Firebase dependencies
    implementation(platform(Dependencies.Firebase.BOM))
    implementation(Dependencies.Firebase.AUTH)
    implementation(Dependencies.Firebase.FIRESTORE)
    implementation(Dependencies.Firebase.PLAY_SERVICES_AUTH)
    implementation(Dependencies.Firebase.COROUTINES_PLAY_SERVICES)
    implementation(Dependencies.Firebase.ANALYTICS)

    // Network dependencies
    implementation(Dependencies.Network.GSON)
    implementation("androidx.navigation:navigation-common-ktx:2.5.3")
    implementation("com.google.firebase:firebase-messaging-ktx:23.1.1")

    // Test dependencies
    testImplementation(Dependencies.Test.JUNIT)
    testImplementation(Dependencies.Test.MOCKITO)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    androidTestImplementation(Dependencies.Test.JUNIT_ANDROID)
    androidTestImplementation(Dependencies.Test.ESPRESSO_CORE)

    // Hilt dependencies
    implementation(Dependencies.Hilt.ANDROID)
    kapt(Dependencies.Hilt.ANDROID_COMPILER)
    testImplementation(Dependencies.Hilt.ANDROID_TESTING)
    kaptTest(Dependencies.Hilt.ANDROID_COMPILER)
    testAnnotationProcessor(Dependencies.Hilt.ANDROID_COMPILER)
}