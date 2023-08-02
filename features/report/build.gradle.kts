import config.AndroidConfig
import dependencies.Dependencies
import flavor.BuildTypeDebug
import flavor.BuildTypeRelease

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    namespace = "br.com.brunocarvalhs.report"
    compileSdk = AndroidConfig.COMPILE_SDK_VERSION

    defaultConfig {
        minSdk = AndroidConfig.MIN_SDK_VERSION
        targetSdk = AndroidConfig.TARGET_SDK_VERSION

        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER
        consumerProguardFiles("consumer-rules.pro")
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
            isJniDebuggable = BuildTypeDebug.isDebuggable
        }
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
    compileOptions {
        sourceCompatibility = AndroidConfig.JAVA_VERSION
        targetCompatibility = AndroidConfig.JAVA_VERSION
    }
    kotlinOptions {
        jvmTarget = AndroidConfig.JAVA_VERSION.toString()
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
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation(Dependencies.UI.CONSTRAINT_LAYOUT)
    implementation(Dependencies.UI.COORDINATOR_LAYOUT)
    implementation(Dependencies.UI.NAVIGATION_FRAGMENT)
    implementation(Dependencies.UI.NAVIGATION_UI)
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")

    // Test dependencies
    testImplementation(Dependencies.Test.JUNIT)
    testImplementation(Dependencies.Test.MOCKITO)
    testImplementation("androidx.test.ext:junit-ktx:1.1.5")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    androidTestImplementation(Dependencies.Test.JUNIT_ANDROID)
    androidTestImplementation(Dependencies.Test.ESPRESSO_CORE)

    // Hilt dependencies
    implementation(Dependencies.Hilt.ANDROID)
    kapt(Dependencies.Hilt.ANDROID_COMPILER)
    kapt("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.5.0")

    // Glide dependencies
    implementation(Dependencies.Glide.GLIDE)
    kapt(Dependencies.Glide.GLIDE_COMPILER)

    // Chart
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
}