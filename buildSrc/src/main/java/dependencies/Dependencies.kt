package dependencies

object Dependencies {
    object Core {
        const val KTX = "androidx.core:core-ktx:${Versions.Core.KTX}"
        const val LIFECYCLE_RUNTIME =
            "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.Core.LIFECYCLE_RUNTIME}"
        const val LIFECYCLE_LIVEDATA =
            "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.Core.LIFECYCLE_RUNTIME}"
        const val LIFECYCLE_VIEWMODEL =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.Core.LIFECYCLE_RUNTIME}"
        const val MULTIDEX = "androidx.multidex:multidex:${Versions.Core.MULTIDEX}"
    }

    object UI {
        const val MATERIAL = "com.google.android.material:material:${Versions.UI.MATERIAL}"
        const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:${Versions.UI.FRAGMENT_KTX}"
        const val LIFECYCLE_VIEWMODEL_KTX =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.UI.LIFECYCLE_VIEWMODEL_KTX}"
        const val APPCOMPAT = "androidx.appcompat:appcompat:${Versions.UI.APPCOMPAT}"
        const val DATABINDING_COMMON =
            "androidx.databinding:databinding-common:${Versions.UI.DATABINDING_COMMON}"
        const val DATABINDING_COMPILER =
            "androidx.databinding:databinding-compiler:${Versions.UI.DATABINDING_COMPILER}"
        const val CONSTRAINT_LAYOUT =
            "androidx.constraintlayout:constraintlayout:${Versions.UI.CONSTRAINT_LAYOUT}"
        const val COORDINATOR_LAYOUT =
            "androidx.coordinatorlayout:coordinatorlayout:${Versions.UI.COORDINATOR_LAYOUT}"
        const val NAVIGATION_FRAGMENT =
            "androidx.navigation:navigation-fragment-ktx:${Versions.UI.NAVIGATION_FRAGMENT}"
        const val NAVIGATION_UI =
            "androidx.navigation:navigation-ui-ktx:${Versions.UI.NAVIGATION_UI}"
    }

    object Test {
        const val JUNIT = "junit:junit:${Versions.Test.JUNIT}"
        const val JUNIT_ANDROID = "androidx.test.ext:junit:${Versions.Test.JUNIT_ANDROID}"
        const val ESPRESSO_CORE =
            "androidx.test.espresso:espresso-core:${Versions.Test.ESPRESSO_CORE}"
        const val MOCKITO = "org.mockito:mockito-core:${Versions.Test.MOCKITO}"
    }

    object Navigation {
        const val NAVIGATION_RUNTIME =
            "androidx.navigation:navigation-runtime-ktx:${Versions.Navigation.NAVIGATION_RUNTIME}"
    }

    object Hilt {
        const val ANDROID = "com.google.dagger:hilt-android:${Versions.Hilt.ANDROID}"
        const val ANDROID_COMPILER =
            "com.google.dagger:hilt-android-compiler:${Versions.Hilt.ANDROID_COMPILER}"
        const val ANDROID_TESTING =
            "com.google.dagger:hilt-android-testing:${Versions.Hilt.ANDROID_TESTING}"
    }

    object Firebase {
        const val BOM = "com.google.firebase:firebase-bom:${Versions.Firebase.BOM}"
        const val CRASHLYTICS =
            "com.google.firebase:firebase-crashlytics-ktx:${Versions.Firebase.CRASHLYTICS}"
        const val ANALYTICS =
            "com.google.firebase:firebase-analytics-ktx:${Versions.Firebase.ANALYTICS}"
        const val AUTH = "com.google.firebase:firebase-auth-ktx:${Versions.Firebase.AUTH}"
        const val FIRESTORE =
            "com.google.firebase:firebase-firestore-ktx:${Versions.Firebase.FIRESTORE}"
        const val PLAY_SERVICES_AUTH =
            "com.google.android.gms:play-services-auth:${Versions.Firebase.PLAY_SERVICES_AUTH}"
        const val COROUTINES_PLAY_SERVICES =
            "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.Firebase.COROUTINES_PLAY_SERVICES}"
        const val UI_AUTH = "com.firebaseui:firebase-ui-auth:${Versions.Firebase.UI_AUTH}"
    }

    object Network {
        const val GSON = "com.google.code.gson:gson:${Versions.Network.GSON}"
        const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.Network.RETROFIT}"
        const val RETROFIT_GSON =
            "com.squareup.retrofit2:converter-gson:${Versions.Network.RETROFIT_GSON}"
        const val OKHTTP = "com.squareup.okhttp3:okhttp:${Versions.Network.OKHTTP}"
        const val OKHTTP_LOGGING =
            "com.squareup.okhttp3:logging-interceptor:${Versions.Network.OKHTTP_LOGGING}"
    }

    object Glide {
        const val GLIDE = "com.github.bumptech.glide:glide:${Versions.Glide.GLIDE}"
        const val GLIDE_COMPILER =
            "com.github.bumptech.glide:compiler:${Versions.Glide.GLIDE_COMPILER}"
    }
}