import config.AndroidConfig
import interfaces.BuildType
import flavor.BuildTypeRelease
import flavor.BuildTypeDebug
import dependencies.Dependencies

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = AndroidConfig.APP_ID + ".commons"
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
    buildFeatures {
        viewBinding = true
    }
    kotlinOptions {
        jvmTarget = AndroidConfig.JAVA_VERSION.toString()
    }
}

dependencies {
    // Core dependencies
    implementation(Dependencies.Core.KTX)

    // UI dependencies
    implementation(Dependencies.UI.MATERIAL)
    implementation(Dependencies.UI.FRAGMENT_KTX)
    implementation(Dependencies.UI.LIFECYCLE_VIEWMODEL_KTX)
    implementation(Dependencies.UI.APPCOMPAT)

    // Test dependencies
    testImplementation(Dependencies.Test.JUNIT)
    androidTestImplementation(Dependencies.Test.JUNIT_ANDROID)
    androidTestImplementation(Dependencies.Test.ESPRESSO_CORE)
}