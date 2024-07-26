plugins {
    alias(libs.plugins.sample.android.application)
}

android {
    namespace = "com.example.chat"
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // lifecycle compose
    implementation(libs.lifecycle.runtime.compose)

    // navigation compose
    implementation(libs.navigation.compose)

    // coil
    implementation(libs.coil.compose)

    implementation(libs.androidx.material3)

    implementation(libs.kotlinxCollectionsImmutable)
}