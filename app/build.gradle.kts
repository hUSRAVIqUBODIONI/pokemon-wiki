plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.pokemon"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.pokemon"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("com.jakewharton.timber:timber:5.0.1")

    implementation("androidx.navigation:navigation-compose:2.7.7")

    implementation("com.google.dagger:hilt-android:2.56.2")
    ksp("com.google.dagger:hilt-android-compiler:2.56.2")


    // Основная библиотека Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // Конвертер для работы с JSON (выберите один)
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") // для Gson

    // Для поддержки suspend функций
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")

    // Логирование HTTP-запросов (опционально)
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    implementation("androidx.compose.ui:ui-text-google-fonts:1.6.0")

    implementation("io.coil-kt:coil-compose:2.4.0")

    implementation("androidx.palette:palette-ktx:1.0.0")

    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

}