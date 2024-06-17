
import config.AndroidConfig

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
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            isJniDebuggable = false
        }
    }
    buildFeatures {
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = AndroidConfig.JAVA_VERSION
        targetCompatibility = AndroidConfig.JAVA_VERSION
    }
    kotlinOptions {
        jvmTarget = AndroidConfig.JAVA_VERSION.toString()
    }
}

dependencies {
    implementation(project(mapOf("path" to ":domain")))

    // Core dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.app.update.ktx)

    // Firebase dependencies
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.play.services.auth)
    implementation(libs.google.firebase.analytics.ktx)
    implementation(libs.kotlinx.coroutines.play.services)
    implementation(libs.play.services.ads)

    // Network dependencies
    implementation(libs.gson)
    implementation(libs.androidx.navigation.common.ktx)
    implementation(libs.firebase.messaging.ktx)
    implementation(libs.material)

    // Test dependencies
    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Hilt dependencies
    implementation(libs.hilt.android)
    kapt(libs.dagger.hilt.android.compiler)
    testImplementation(libs.hilt.android.testing)
    kaptTest(libs.dagger.hilt.android.compiler)
    testAnnotationProcessor(libs.dagger.hilt.android.compiler)
}