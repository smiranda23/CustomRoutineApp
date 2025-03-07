import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    kotlin("plugin.serialization") version "2.0.21" //Misma version que en InteliJ
    id("app.cash.sqldelight") version "2.0.1"
    alias(libs.plugins.cocoapods)

}

sqldelight {
    databases {
        create("DatabaseBF") {
            packageName.set("com.basicfactory.db")
        }
    }
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

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "15.4"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }



    sourceSets {

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            //ktor
            implementation(libs.ktor.client.okhttp)

            //Corrutinas
            implementation(libs.kotlinx.coroutines.android)

            //KOIN
            implementation(project.dependencies.platform("io.insert-koin:koin-bom:3.5.1"))
            implementation("io.insert-koin:koin-core")
            implementation("io.insert-koin:koin-android")

            //sqldelight
            implementation("app.cash.sqldelight:android-driver:2.0.2")



        }
        commonMain.dependencies {
            implementation(compose.runtime)
            //implementation(compose.foundation)
            api(compose.foundation)
            api(compose.animation)
            implementation(compose.material)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            //ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.serialization)
            implementation(libs.ktor.content.negotiation)

            //Corrutinas - PERTENCE TMB A Ktor
            implementation(libs.kotlinx.coroutines.core)

            //Navigation PreCompose
            //"1.5.10" //Usamos misma version que la del curso de udemy
            val precompose_version = "1.5.10"
            api("moe.tlaster:precompose:$precompose_version")

            //ViewModel PreCompose
            api("moe.tlaster:precompose-viewmodel:$precompose_version")

            //Koin
            implementation(project.dependencies.platform("io.insert-koin:koin-bom:3.5.1"))
            implementation("io.insert-koin:koin-core")
            implementation("io.insert-koin:koin-compose")
            api("moe.tlaster:precompose-koin:$precompose_version")

            //FECHAS
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0") // Asegúrate de verificar la última versión

        }

        iosMain.dependencies {
            //iOS dependencies



            //ktor
            implementation(libs.ktor.client.darwin)
            //sqldelight
            implementation("app.cash.sqldelight:native-driver:2.0.1")
            //implementation("app.cash.sqldelight:sqlite-driver:2.0.2") // Asegúrate de agregar el driver SQLite

            //stately-common
            implementation(libs.stately.common)


        }
    }
}

android {
    namespace = "org.basicfactorysm"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.basicfactorysm"
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
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.animation.core.android)
    debugImplementation(compose.uiTooling)
}

