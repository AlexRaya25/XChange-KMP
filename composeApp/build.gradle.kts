import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    kotlin("plugin.serialization") version "1.9.0"
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    jvm("desktop")

//    @OptIn(ExperimentalWasmDsl::class)
  //  wasmJs {
    //    moduleName = "composeApp"
      //  browser {
        //    val rootDirPath = project.rootDir.path
          //  val projectDirPath = project.projectDir.path
            //commonWebpackConfig {
              //  outputFileName = "composeApp.js"
                //devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                  //  static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                    //    add(rootDirPath)
                      //  add(projectDirPath)
                    //}
                //}
            //}
        //}
        //binaries.executable()
    //}

    sourceSets {
        val desktopMain by getting {
            dependencies {
                val osName = System.getProperty("os.name").lowercase()
                val platform = when {
                    osName.contains("win") -> "win"
                    osName.contains("nux") -> "linux"
                    osName.contains("mac") -> "mac"
                    else -> throw GradleException("Unsupported OS: $osName")
                }

                implementation(compose.desktop.currentOs)
                implementation(libs.kotlinx.coroutines.swing)
                implementation("org.openjfx:javafx-base:19:$platform")
                implementation("org.openjfx:javafx-graphics:19:$platform")
                implementation("org.openjfx:javafx-controls:19:$platform")
                implementation("org.openjfx:javafx-swing:19:$platform")
                implementation("org.openjfx:javafx-web:19:$platform")
                implementation("org.openjfx:javafx-media:19:$platform")
                // Add other desktop-specific dependencies here
            }
        }

        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.json)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation("io.ktor:ktor-client-serialization:2.3.0")
                implementation("io.ktor:ktor-client-logging:2.3.0")
                implementation(libs.ktor.client.cio)
                implementation(libs.ktor.client.content.negotiation)
                implementation("io.insert-koin:koin-core:3.5.0")
                implementation(libs.androidx.lifecycle.viewmodel)
                implementation(libs.androidx.lifecycle.runtime.compose)
                implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(compose.preview)
                implementation(libs.androidx.activity.compose)
                implementation("io.insert-koin:koin-androidx-compose:3.5.0")
                implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
            }
        }
    }
}

android {
    namespace = "com.rayadev.xchange"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.rayadev.xchange"
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
    implementation(libs.androidx.lifecycle.viewmodel.android)
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "com.rayadev.xchange.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.rayadev.xchange"
            packageVersion = "1.0.0"
        }
    }
}
