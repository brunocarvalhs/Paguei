import config.AndroidConfig

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.firebase.firebase-perf")
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

        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    android {
        signingConfigs {
            create("release") {
                val keyStorePassword = System.getenv("KEYSTORE_PASSWORD")
                val keyStoreAlias = System.getenv("KEYSTORE_ALIAS")
                val keyStoreAliasPassword = System.getenv("KEY_PASSWORD")
                if (keyStorePassword != null && keyStoreAlias != null && keyStoreAliasPassword != null) {
                    storeFile = file("release.keystore")
                    storePassword = keyStorePassword
                    keyAlias = keyStoreAlias
                    keyPassword = keyStoreAliasPassword
                }
            }
        }
        buildTypes {
            release {
                resValue("string", "app_name", "Paguei!")
                resValue("string", "ID_APP_AD_MOB", "ca-app-pub-1765514781734091~6485504377")

                isDebuggable = false
                isJniDebuggable = false
                signingConfig = signingConfigs.getByName("release")
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
                )
            }
            debug {
                resValue("string", "app_name", "Paguei! - Debug")
                resValue("string", "ID_APP_AD_MOB", "ca-app-pub-1765514781734091~1018252717")

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
        jvmTarget = AndroidConfig.JAVA_VERSION.toString()
    }
    buildFeatures {
        buildConfig = true
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
    implementation(project(mapOf("path" to ":features:splash")))
    implementation(project(mapOf("path" to ":features:auth")))
    implementation(project(mapOf("path" to ":features:billet_registration")))
    implementation(project(mapOf("path" to ":features:costs")))
    implementation(project(mapOf("path" to ":features:extracts")))
    implementation(project(mapOf("path" to ":features:groups")))
    implementation(project(mapOf("path" to ":features:profile")))
    implementation(project(mapOf("path" to ":features:report")))
    implementation(project(mapOf("path" to ":features:calculation")))
    implementation(project(mapOf("path" to ":features:check_list")))

    // Modules
    implementation(project(mapOf("path" to ":data")))
    implementation(project(mapOf("path" to ":domain")))
    implementation(project(mapOf("path" to ":commons")))

    // Core dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.multidex)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // UI dependencies
    implementation(libs.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.databinding.common)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.coordinatorlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.firebase.perf.ktx)

    // Test dependencies
    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.androidx.junit.ktx)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Hilt dependencies
    implementation(libs.hilt.android)
    kapt(libs.dagger.hilt.android.compiler)
    testImplementation(libs.hilt.android.testing)
    kaptTest(libs.dagger.hilt.android.compiler)
    testAnnotationProcessor(libs.google.hilt.android.compiler)

    // Firebase dependencies
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics.ktx)
    implementation(libs.firebase.analytics.ktx)
    implementation(libs.kotlinx.coroutines.play.services)
    implementation(libs.firebase.inappmessaging.display.ktx)

    // Compose
    implementation(platform(libs.compose.bom))
    androidTestImplementation(platform(libs.compose.bom))
    implementation(libs.material3)
    implementation(libs.androidx.material)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.material.icons.core)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.material3.window.size)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.navigation.compose)
}
