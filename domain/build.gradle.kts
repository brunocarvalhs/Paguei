import config.AndroidConfig

plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("kotlinx-serialization")
}

java {
    sourceCompatibility = AndroidConfig.JAVA_VERSION
    targetCompatibility = AndroidConfig.JAVA_VERSION
}
