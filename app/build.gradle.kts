import config.AndroidConfig
import dependencies.Dependencies

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = AndroidConfig.APP_ID
    compileSdk = AndroidConfig.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = AndroidConfig.APP_ID
        minSdk = AndroidConfig.MIN_SDK_VERSION
        targetSdk = AndroidConfig.TARGET_SDK_VERSION
        versionCode = AndroidConfig.VERSION_CODE
        versionName = AndroidConfig.VERSION_NAME
        multiDexEnabled = true
        buildToolsVersion = "33.0.1"

        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    android {
        buildTypes {
            getByName("release") {
                resValue("string", "app_name", "Paguei!")

                isDebuggable = false
                isJniDebuggable = false
                isMinifyEnabled = true
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
                )
            }
            getByName("debug") {
                resValue("string", "app_name", "Paguei! - Debug")

                applicationIdSuffix = ".debug"
                isMinifyEnabled = false
                isDebuggable = true
            }
        }
    }
    compileOptions {
        sourceCompatibility = AndroidConfig.JAVA_VERSION
        targetCompatibility = AndroidConfig.JAVA_VERSION
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    lint {
        abortOnError = false
        disable += "MissingTranslation"
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    // Features
    implementation(project(mapOf("path" to ":features:auth")))
    implementation(project(mapOf("path" to ":features:billet_registration")))
    implementation(project(mapOf("path" to ":features:costs")))
    implementation(project(mapOf("path" to ":features:extracts")))
    implementation(project(mapOf("path" to ":features:groups")))
    implementation(project(mapOf("path" to ":features:profile")))
    implementation(project(mapOf("path" to ":features:report")))

    // Modules
    implementation(project(mapOf("path" to ":data")))
    implementation(project(mapOf("path" to ":domain")))
    implementation(project(mapOf("path" to ":commons")))

    // Core dependencies
    implementation(Dependencies.Core.KTX)
    implementation(Dependencies.Core.MULTIDEX)
    implementation(Dependencies.Core.LIFECYCLE_RUNTIME)
    implementation(Dependencies.Core.LIFECYCLE_LIVEDATA)
    implementation(Dependencies.Core.LIFECYCLE_VIEWMODEL)

    // UI dependencies
    implementation(Dependencies.UI.MATERIAL)
    implementation(Dependencies.UI.APPCOMPAT)
    implementation(Dependencies.UI.FRAGMENT_KTX)
    implementation(Dependencies.UI.DATABINDING_COMMON)
    implementation(Dependencies.UI.CONSTRAINT_LAYOUT)
    implementation(Dependencies.UI.COORDINATOR_LAYOUT)
    implementation(Dependencies.UI.NAVIGATION_FRAGMENT)
    implementation(Dependencies.UI.NAVIGATION_UI)

    // Test dependencies
    testImplementation(Dependencies.Test.JUNIT)
    androidTestImplementation(Dependencies.Test.JUNIT_ANDROID)
    androidTestImplementation(Dependencies.Test.ESPRESSO_CORE)

    // Hilt dependencies
    implementation(Dependencies.Hilt.ANDROID)
    kapt(Dependencies.Hilt.ANDROID_COMPILER)
    kapt("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.5.0")

    // Firebase dependencies
    implementation(platform(Dependencies.Firebase.BOM))
    implementation(Dependencies.Firebase.CRASHLYTICS)
    implementation(Dependencies.Firebase.ANALYTICS)
    implementation(Dependencies.Firebase.COROUTINES_PLAY_SERVICES)
    implementation("com.google.firebase:firebase-inappmessaging-display-ktx")
}
