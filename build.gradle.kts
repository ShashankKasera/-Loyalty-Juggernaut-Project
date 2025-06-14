// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.devtool.ksp).version(libs.versions.ksp.get()).apply(false)
    alias (libs.plugins.dagger.hilt).version(libs.versions.hiltAndroidVersion.get()).apply(false)
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
}