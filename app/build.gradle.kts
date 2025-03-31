plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.serialization)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.app_comedor"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.app_comedor"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        buildConfig = true
    }
    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = false
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            resValue( type= "string", name= "app_name", value= "App_comedor dev")
            buildConfigField("String", "HOST", rootProject.ext.properties["HOST"].toString()
            )
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            resValue( type= "string", name= "app_name", value= "App_comedor dev")
            buildConfigField("String", "HOST", rootProject.ext.properties["HOST"].toString()
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    //Timber
    implementation(libs.timber)

    //Ktor
    implementation( libs.ktor.client.core)
    implementation( libs.ktor.client.android)
    implementation( libs.ktor.client.content.negotiation)
    implementation( libs.ktor.serialization.kotlinx.json)
    implementation( libs.ktor.client.logging)

    //koin
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.koin.compose.viewmodel)
    implementation(libs.koin.compose.viewmodel.navigation)
    testImplementation(libs.koin.test.junit4)
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j)
    //navigations_compose
    implementation(libs.androidx.navigation.compose)

    //Room]
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}