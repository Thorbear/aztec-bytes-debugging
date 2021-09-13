plugins {
    id("com.android.application")
    id("gradle-versions-plugin")
    kotlin("android")
}

android {
    compileSdk = 30

    defaultConfig {
        applicationId = "no.thorbear.android.aztec.debugging"
        minSdk = 23
        multiDexEnabled = true
        targetSdk = 30

        versionCode = 1
        versionName = "$versionCode"
        vectorDrawables.useSupportLibrary = true
        setProperty("archivesBaseName", "Aztec Bytes Debugging v$versionName")
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles("proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidxAnnotation)
    // one of the other dependencies are using an outdated version of this that causes lint warnings
    implementation(libs.androidxAnnotationExperimental)
    implementation(libs.androidxAppcompat)
    implementation(libs.androidxConstraintlayout)
    // Used by androidxPreference, forcing newer version to silence lint warnings
    implementation(libs.androidxLifecycleRuntime)
    implementation(libs.androidxMultidex)
    implementation(libs.androidxPreference)
    implementation(libs.kotlin)
    implementation(libs.mlkitBarcodeScanning)
    implementation(libs.rxjava2Rxandroid)
    implementation(libs.rxjava2Rxjava)
    implementation(libs.rxjava2Rxkotlin)
}
