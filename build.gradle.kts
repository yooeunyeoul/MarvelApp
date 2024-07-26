plugins {
    alias(libs.plugins.androidApplication) apply false
//    alias(libs.plugins.jetbrainsKotlinJvm) apply false
//    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
//    alias(libs.plugins.kapt) apply false
    alias(libs.plugins.sample.android.application) apply false
    alias(libs.plugins.sample.android.library) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.sample.compose) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.jetbrainsKotlinJvm) apply false
//    alias(libs.plugins.hiltPlugin) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.android.tools.build.gradle.plugin)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.hilt.gradle.plugin)
    }
}

allprojects {
    tasks.withType(org.jetbrains.kotlin.gradle.dsl.KotlinCompile::class.java).configureEach {
        kotlinOptions {
            // Trigger this with:
            // ./gradlew assembleRelease -PenableMultiModuleComposeReports=true --rerun-tasks
            if (project.findProperty("enableMultiModuleComposeReports") == "true") {
                freeCompilerArgs += listOf("-P", "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" + rootProject.buildDir.absolutePath + "/compose_metrics/")
                freeCompilerArgs += listOf("-P", "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" + rootProject.buildDir.absolutePath + "/compose_metrics/")
            }
        }
    }
}