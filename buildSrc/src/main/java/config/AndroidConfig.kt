package config

import org.gradle.api.JavaVersion
import java.io.File

object AndroidConfig {
    const val COMPILE_SDK_VERSION = 33
    const val MIN_SDK_VERSION = 24
    const val TARGET_SDK_VERSION = 33

    const val VERSION_CODE = 22
    const val VERSION_NAME = "8.2.0"

    const val APP_ID = "br.com.brunocarvalhs.paguei"
    const val TEST_INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"

    val JAVA_VERSION = JavaVersion.VERSION_1_8
}
