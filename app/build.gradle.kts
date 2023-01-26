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

        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    android {
        signingConfigs {
            create("release") {
                storeFile = file(System.getenv("KEYSTORE_PATH") ?: "keystore.jks")
                storePassword = System.getenv("KEYSTORE_PASSWORD") ?: ""
                keyAlias = System.getenv("KEYSTORE_ALIAS") ?: ""
                keyPassword = System.getenv("KEYSTORE_ALIAS_PASSWORD") ?: ""
                enableV1Signing = true
                enableV2Signing = true
            }
        }
        buildTypes {
            getByName("release") {
                isDebuggable = false
                isJniDebuggable = false
                signingConfig = signingConfigs.getByName("release")
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
                )
            }
            getByName("debug") {
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
    // Modules
    implementation(project(mapOf("path" to ":data")))
    implementation(project(mapOf("path" to ":domain")))
    implementation(project(mapOf("path" to ":commons")))

    // Core dependencies
    implementation(Dependencies.Core.KTX)
    implementation(Dependencies.Core.LIFECYCLE_RUNTIME)
    implementation(Dependencies.Core.LIFECYCLE_LIVEDATA)
    implementation(Dependencies.Core.LIFECYCLE_VIEWMODEL)

    // UI dependencies
    implementation(Dependencies.UI.MATERIAL)
    implementation(Dependencies.UI.APPCOMPAT)
    implementation(Dependencies.UI.FRAGMENT_KTX)
    implementation(Dependencies.UI.DATABINDING_COMMON)
    implementation("com.google.android.material:material:1.7.0")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.test.ext:junit-ktx:1.1.3")
    implementation(Dependencies.UI.CONSTRAINT_LAYOUT)
    implementation(Dependencies.UI.COORDINATOR_LAYOUT)
    implementation(Dependencies.UI.NAVIGATION_FRAGMENT)
    implementation(Dependencies.UI.NAVIGATION_UI)

    // Test dependencies
    testImplementation(Dependencies.Test.JUNIT)
    testImplementation(Dependencies.Test.MOCKITO)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
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
    implementation(Dependencies.Firebase.AUTH)
    implementation(Dependencies.Firebase.FIRESTORE)
    implementation(Dependencies.Firebase.PLAY_SERVICES_AUTH)
    implementation(Dependencies.Firebase.COROUTINES_PLAY_SERVICES)
    implementation(Dependencies.Firebase.UI_AUTH)

    // Network dependencies
    implementation(Dependencies.Network.GSON)

    // Glide dependencies
    implementation(Dependencies.Glide.GLIDE)
    kapt(Dependencies.Glide.GLIDE_COMPILER)

    // Camera
    implementation("androidx.camera:camera-core:1.2.0")
    implementation("androidx.camera:camera-view:1.2.0")
    implementation("androidx.camera:camera-camera2:1.2.0")
    implementation("androidx.camera:camera-lifecycle:1.2.0")
    implementation("androidx.camera:camera-extensions:1.2.0")
    implementation("com.google.mlkit:barcode-scanning:17.0.3")

    // Mascara
    implementation("com.redmadrobot:input-mask-android:6.1.0")
}
