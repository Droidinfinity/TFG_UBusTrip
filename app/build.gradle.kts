import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
    id("io.realm.kotlin")
    id("org.jetbrains.dokka") version "1.9.10"
}

subprojects{
    apply(plugin = "org.jetbrains.dokka")
}

android {
    namespace = "com.upm.ubustrip"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.upm.ubustrip"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0-alpha.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }


        val localProps = Properties().apply {
            val file = rootProject.file("local.properties")
            if (file.exists()) {
                load(file.inputStream())
            }
        }
        val apiKey = localProps.getProperty("GOOGLE_MAPS_API_KEY") ?: ""
        resValue("string", "GOOGLE_MAPS_API_KEY", apiKey)

        buildConfigField("String", "GOOGLE_MAPS_API_KEY", "\"$apiKey\"")



    }
    packaging {

        resources {
            excludes += "META-INF/native-image/native-image.properties"
            excludes += "META-INF/native-image/reflect-config.json"
        }
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
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
    implementation("androidx.navigation:navigation-compose:2.8.3")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.30.1")
    implementation("androidx.compose.ui:ui-text-google-fonts:1.6.0")
    implementation(platform("com.google.firebase:firebase-bom:33.6.0"))
    implementation("com.google.android.gms:play-services-auth:21.2.0")
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("io.realm.kotlin:library-base:1.16.0")

    implementation("org.mongodb:bson-kotlinx:5.2.1")

    implementation("com.squareup.okhttp3:okhttp:4.11.0")

    implementation("androidx.compose.material:material-icons-extended:1.6.0")

    // Android Maps Compose composables for the Maps SDK for Android
    implementation("com.google.maps.android:maps-compose:6.4.1")
    implementation ("com.google.android.gms:play-services-maps:18.2.0")

    implementation("androidx.datastore:datastore-preferences:1.1.1")

    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.firebase.auth.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation ("org.mockito:mockito-core:4.5.1")
    testImplementation ("org.mockito.kotlin:mockito-kotlin:4.1.0")
    testImplementation ("io.mockk:mockk:1.13.8")
}

tasks{
    dokkaHtml {
        outputDirectory.set(file("C:\\Users\\agusw\\Documents\\TFG\\docs\\dokka\\html"))
        dokkaSourceSets {
            configureEach {
                reportUndocumented.set(false)  // Omito advertencias por métodos no documentados
            }
        }
        
    }

}