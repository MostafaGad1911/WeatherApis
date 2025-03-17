plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlinx-serialization")
    id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply true
}

android {
    namespace = "gad.weatherapicheck"
    compileSdk = 35

    defaultConfig {
        applicationId = "gad.weatherapicheck"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    // Compose
    //noinspection UseTomlInstead
    implementation ("androidx.compose.ui:ui:1.7.8")
    implementation (libs.androidx.material)
    implementation(libs.coil.compose)

    // Lifecycle
    implementation (libs.androidx.lifecycle.runtime.ktx.v287)
    implementation (libs.androidx.lifecycle.viewmodel.compose)

    // Navigation
    implementation (libs.androidx.navigation.compose)
    implementation (libs.androidx.navigation.runtime)
    implementation (libs.androidx.navigation.common)


    // Koin
    implementation (libs.koin.android)
    implementation (libs.koin.androidx.compose)

    // Location
    implementation (libs.play.services.location)

    // Permissions
    implementation (libs.accompanist.permissions)

    // Ktor
    implementation (libs.ktor.client.core)
    implementation (libs.ktor.client.cio)
    implementation (libs.ktor.client.serialization)
    implementation (libs.ktor.serialization.kotlinx.json)
    implementation (libs.ktor.client.content.negotiation)
    implementation (libs.ktor.serialization.kotlinx.json.v2xx)

    // Coroutines
    implementation (libs.kotlinx.coroutines.core)
    implementation (libs.kotlinx.coroutines.android)


    // Room
    implementation (libs.androidx.room.runtime)
    implementation (libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.play.services.location)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}