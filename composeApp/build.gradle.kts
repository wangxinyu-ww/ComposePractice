import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
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

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.material.icons.core)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.jetbrains.kotlinx.coroutines.core)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

android {
    namespace = "com.xywang.practice"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.xywang.practice"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "com.xywang.practice.MainKt"

        nativeDistributions {
            // 基础设置
            packageName = "PracticeApp"
            packageVersion = "1.0.0"
            description = "A cross-platform app built with Kotlin Multiplatform and Jetpack Compose"
            copyright = "© 2025 XY Wang"
            vendor = "XY Wang Developer Studio"

            // 输出目录（可选）
            outputBaseDir.set(layout.buildDirectory.dir("composeDist"))

            // 图标设置（针对不同平台）
            macOS {
                iconFile.set(project.file("src/desktopMain/resources/icon.icns"))
                bundleID = "com.xywang.practice"
            }
            windows {
                iconFile.set(project.file("src/desktopMain/resources/icon.ico"))
                menuGroup = "Practice Tools"
                upgradeUuid = "E3D5C894-8E54-4F50-9A3E-123456789ABC" // 固定 UUID 便于升级
                dirChooser = true  // 允许用户选择安装目录
                perUserInstall = true  // 允许非管理员安装
                shortcut = true  // 创建桌面快捷方式
            }

            linux {
                iconFile.set(project.file("src/desktopMain/resources/icon.png"))
                appCategory = "Education"
            }
            targetFormats(
                // Windows 格式
                TargetFormat.Msi,    // Windows 安装包
                TargetFormat.Exe,    // Windows 可执行安装程序

                // macOS 格式
                TargetFormat.Dmg,    // macOS 磁盘镜像
                TargetFormat.Pkg,    // macOS 安装包

                // Linux 格式
                TargetFormat.Deb,    // Debian/Ubuntu
                TargetFormat.Rpm     // Red Hat/Fedora/CentOS
            )
            // License 文件（可选）
            licenseFile.set(project.file("../LICENSE.txt"))
        }
    }
}
