plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.bridge.androidtechnicaltest"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
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

   buildFeatures{

       viewBinding = true

   }
}

dependencies {
    // Koin
    implementation(libs.koin.android)

    // AndroidX libraries
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.google.material)

    // Room (use KSP for Room compiler)
    implementation(libs.room.ktx)
    implementation(libs.androidx.room.rxjava2)
    ksp(libs.room.compiler)

    // RxJava and RxAndroid
    implementation(libs.rxandroid)
    implementation(libs.rxjava)

    // Retrofit + OkHttp
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson.convertor)
    implementation(libs.retrofit.adapter.rxjava2)
    implementation(libs.okhttp)

    // WorkManager + Hilt integration
    implementation(libs.work.manager)
    implementation(libs.work.manager.dagger)
    ksp(libs.work.manager.dagger.kapt)

    // Hilt dependencies
    implementation(libs.dagger.hilt)
    kapt(libs.hilt.android.compiler)

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)

    implementation(libs.okhttp.logging)
}
