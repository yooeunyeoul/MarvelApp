plugins {
    alias(libs.plugins.sample.android.application)
    alias(libs.plugins.sample.compose)
}

android {
    namespace = "com.example.intro_test"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.accompanist.pager)

    // coil
    implementation(libs.coil.compose)
}