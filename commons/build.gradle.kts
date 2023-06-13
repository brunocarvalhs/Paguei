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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.2"
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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

    // Mask
    implementation("com.redmadrobot:input-mask-android:6.1.0")

    // Compose
    implementation(platform(Dependencies.Compose.BOM))
    androidTestImplementation(platform(Dependencies.Compose.BOM))
    implementation(Dependencies.Compose.MATERIAL_YOU)
    implementation(Dependencies.Compose.MATERIAL)
    implementation(Dependencies.Compose.FOUNDATION)
    implementation(Dependencies.Compose.UI)
    implementation(Dependencies.Compose.UI_TOOLING_PREVIEW)
    debugImplementation(Dependencies.Compose.UI_TOOLING_PREVIEW_DEBUG)
    androidTestImplementation(Dependencies.Compose.UI_TEST)
    debugImplementation(Dependencies.Compose.UI_TEST_MANIFEST)
    implementation(Dependencies.Compose.MATERIAL_ICONS_CORE)
    implementation(Dependencies.Compose.MATERIAL_ICONS_EXTENDED)
    implementation(Dependencies.Compose.MATERIAL_WINDOW)
    implementation(Dependencies.Compose.ACTIVITY)
    implementation(Dependencies.Compose.VIEW_MODEL)
    implementation(Dependencies.Compose.LIVE_DATA)
    implementation(Dependencies.Compose.NAVIGATION)
}