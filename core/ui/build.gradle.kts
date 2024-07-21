plugins {
    alias(libs.plugins.sample.android.application)
}

android {
    namespace = "com.example.ui"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.material3)
    implementation(libs.material)

    implementation(libs.coil.compose)

    implementation(libs.kotlinxCollectionsImmutable)
}