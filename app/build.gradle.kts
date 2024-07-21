import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.sample.android.application)
    alias(libs.plugins.sample.compose)

}

android {
    namespace = "com.example.marvelapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.marvelapp"
        minSdk = 31
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        multiDexEnabled = true

//        val properties = gradleLocalProperties(rootDir)
//        val marvelPublicKey = properties["MARVEL_PUBLIC_KEY"] ?: ""
//        val marvelPrivateKey = properties["MARVEL_PRIVATE_KEY"] ?: ""
//
//        buildConfigField("String", "MARVEL_PUBLIC_KEY", "\"$marvelPublicKey\"")
//        buildConfigField("String", "MARVEL_PRIVATE_KEY", "\"$marvelPrivateKey\"")
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
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_1_8
//        targetCompatibility = JavaVersion.VERSION_1_8
//    }
//    kotlinOptions {
//        jvmTarget = "1.8"
//    }
    buildFeatures {
//        compose = true
        buildConfig = true
    }
//    composeOptions {
//        kotlinCompilerExtensionVersion = "1.5.1"
//    }
//    packaging {
//        resources {
//            excludes += "/META-INF/{AL2.0,LGPL2.1}"
//        }
//    }
}

dependencies {

    dependencies {
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
//        implementation(libs.androidx.activity.compose)
//        implementation(platform(libs.androidx.compose.bom))
//        implementation(libs.androidx.ui)
//        implementation(libs.androidx.ui.graphics)
//        implementation(libs.androidx.ui.tooling.preview)
//        implementation(libs.androidx.material3)

        // lifecycle compose
        implementation(libs.lifecycle.runtime.compose)

        // navigation compose
        implementation(libs.navigation.compose)

        // Hilt
        implementation(libs.hilt.android)
        kapt(libs.hilt.compiler)
        implementation(libs.hilt.navigation.compose)

        // retrofit
        implementation(libs.retrofit)
        implementation(libs.retrofit.gson)
        implementation(libs.gson)

        // coil
        implementation(libs.coil.compose)

        // Room
        implementation(libs.room.runtime)
        implementation(libs.room.ktx)
        kapt(libs.room.compiler)

        implementation(libs.okhttp)
        implementation(libs.loggingInterceptor)

        // Test
        testImplementation(libs.junit)
        testImplementation(libs.mockito.core)
        testImplementation(libs.mockito.inline)
        testImplementation(libs.coroutines.test)
        testImplementation(libs.kotlin.test.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        androidTestImplementation(libs.androidx.ui.test.junit4)
        debugImplementation(libs.androidx.ui.tooling)
        debugImplementation(libs.androidx.ui.test.manifest)
        // MockK
        testImplementation(libs.mockk)
        testImplementation(libs.mockk.android)

        implementation(libs.kotlinxCollectionsImmutable)

        implementation(libs.accompanist.pager)
    }

}