import org.gradle.kotlin.dsl.implementation
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("com.google.gms.google-services")
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        androidMain.dependencies {
            // Core e Ciclo de Vida do Android
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.lifecycle.runtime.ktx)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.appcompat)
            implementation(libs.androidx.activity)
            implementation(libs.material)

            // Firebase Nativo
            implementation(libs.firebase.auth.ktx)
            implementation("com.google.firebase:firebase-firestore-ktx:25.1.3")
            implementation("com.google.firebase:firebase-analytics")
            implementation("com.google.firebase:firebase-functions-ktx")
            implementation(project.dependencies.platform("com.google.firebase:firebase-bom:33.12.0"))

            // Leitor de QR Code (ML Kit / Play Services)
            implementation(libs.common)
            implementation(libs.play.services.code.scanner)

            // Accompanist (Para o Pager das suas telas iniciais)
            implementation(libs.accompanist.pager)
            implementation(libs.accompanist.pager.indicators)
            implementation(libs.accompanist.systemuicontroller)

            implementation("androidx.compose.material:material-icons-extended:1.6.1")

            // cofre biometrico
            implementation("androidx.biometric:biometric:1.1.0")
            implementation("androidx.security:security-crypto:1.1.0-alpha06")
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "org.example.superid"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.example.superid"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(libs.compose.uiTooling)
}

