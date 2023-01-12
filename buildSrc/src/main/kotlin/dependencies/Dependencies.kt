package dependencies

object Dependencies {

    const val CORE = "androidx.core:core-ktx:1.9.0"
    const val LIFECYCLE_RUNTIME = "androidx.lifecycle:lifecycle-runtime-ktx:2.5.1"
    const val MATERIAL = "androidx.compose.material:material:1.3.1"
    const val JUNIT_TEST = "junit:junit:4.13.2"
    const val JUNIT_ANDROID_TEST = "androidx.test.ext:junit:1.1.4"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:3.5.0"

    const val HILT_ANDROID = "com.google.dagger:hilt-android:2.44.2"
    const val HILT_ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:2.44.2"

    const val BOM = "com.google.firebase:firebase-bom:31.1.1"
    const val CRASHLYTICS = "com.google.firebase:firebase-crashlytics-ktx"
    const val ANALYTICS = "com.google.firebase:firebase-analytics-ktx"
    const val AUTH = "com.google.firebase:firebase-auth-ktx:21.1.0"
    const val FIRESTORE = "com.google.firebase:firebase-firestore-ktx:24.4.1"
    const val PLAY_SERVICES_AUTH = "com.google.android.gms:play-services-auth:18.1.0"
    const val COROUTINES_PLAY_SERVICES =
        "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4"
    const val GSON = "com.google.code.gson:gson:2.9.0"
}