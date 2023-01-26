package config

import org.gradle.api.JavaVersion

object AndroidConfig {
    const val COMPILE_SDK_VERSION = 33
    const val MIN_SDK_VERSION = 21
    const val TARGET_SDK_VERSION = 33


    const val VERSION_CODE = 7
    const val VERSION_NAME = "3.0.0"

    const val APP_ID = "br.com.brunocarvalhs.paguei"
    const val TEST_INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"

    val JAVA_VERSION = JavaVersion.VERSION_1_8
}