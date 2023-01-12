import config.AndroidConfig
import interfaces.BuildType
import flavor.BuildTypeRelease
import flavor.BuildTypeDebug
import dependencies.Dependencies

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
    id("kotlin-parcelize")
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
        getByName(BuildType.RELEASE) {
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
        getByName(BuildType.DEBUG) {
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
            isJniDebuggable = BuildTypeDebug.isDebuggable
        }
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

    implementation(Dependencies.CORE)
    implementation(Dependencies.MATERIAL)
    implementation("com.google.firebase:firebase-auth-ktx:21.1.0")
    implementation("com.google.android.gms:play-services-auth:20.4.0")
    implementation("com.google.firebase:firebase-firestore-ktx:24.4.1")
    implementation(Dependencies.GSON)
    implementation("com.google.ar:core:1.35.0")
    implementation("androidx.navigation:navigation-runtime-ktx:2.5.3")
    implementation("androidx.appcompat:appcompat:1.5.1")
    testImplementation(Dependencies.JUNIT_TEST)
    testImplementation("org.mockito:mockito-core:4.11.0")
    androidTestImplementation(Dependencies.JUNIT_ANDROID_TEST)
    androidTestImplementation(Dependencies.ESPRESSO_CORE)
    implementation(Dependencies.HILT_ANDROID)
    kapt(Dependencies.HILT_ANDROID_COMPILER)
    testImplementation("com.google.dagger:hilt-android-testing:2.44.2")
    kaptTest("com.google.dagger:hilt-android-compiler:2.44.2")
    testAnnotationProcessor("com.google.dagger:hilt-android-compiler:2.44.2")
}