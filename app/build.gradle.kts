plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")

  kotlin("plugin.serialization") version "1.9.0"
}

android {
  namespace = "dev.tberghuis.widgetglance"
  // todo review
  compileSdk = 34

  defaultConfig {
    applicationId = "dev.tberghuis.widgetglance"
    minSdk = 28
    // todo review
    targetSdk = 33
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    vectorDrawables {
      useSupportLibrary = true
    }
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }
  buildFeatures {
    compose = true
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

  implementation("androidx.core:core-ktx:1.10.1")
  implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
  implementation("androidx.activity:activity-compose:1.7.2")
  implementation(platform("androidx.compose:compose-bom:2023.08.00"))
  implementation("androidx.compose.ui:ui")
  implementation("androidx.compose.ui:ui-graphics")
  implementation("androidx.compose.ui:ui-tooling-preview")
  implementation("androidx.compose.material3:material3")
  testImplementation("junit:junit:4.13.2")
  androidTestImplementation("androidx.test.ext:junit:1.1.5")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
  androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
  androidTestImplementation("androidx.compose.ui:ui-test-junit4")
  debugImplementation("androidx.compose.ui:ui-tooling")
  debugImplementation("androidx.compose.ui:ui-test-manifest")

  implementation("androidx.glance:glance:1.0.0-rc01")
  implementation("androidx.glance:glance-appwidget:1.0.0-rc01")
  implementation("androidx.glance:glance-material3:1.0.0-rc01")

//  implementation("androidx.glance:glance:1.0.0-alpha05")
//  implementation("androidx.glance:glance-appwidget:1.0.0-alpha05")

  implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")


  val ktor_version = "2.3.3"
  implementation("io.ktor:ktor-client-core:$ktor_version")
  implementation("io.ktor:ktor-client-cio:$ktor_version")
  implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
  implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")

}