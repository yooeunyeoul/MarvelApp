plugins {
    alias(libs.plugins.sample.android.library)
    alias(libs.plugins.sample.compose)
}

android {
    namespace = "com.example.on_boarding"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.accompanist.pager)
}