import com.android.build.gradle.BaseExtension

plugins {
    id("com.android.application") version "7.2.1" apply false
    id("com.android.library") version "7.2.1" apply false
    id("org.jetbrains.kotlin.android") version Version.kotlin apply false
}

tasks.register<Delete>("clean").configure {
    delete(rootProject.buildDir)
}
buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.3.10")
    }
}
val apiCode by extra(93)
val verCode = Common.getBuildVersionCode(rootProject)
// versionName = major.minor.bugfix.rev.commit
val verName = "0.0.1" + (Common.getGitHeadRefsSuffix(rootProject))
val androidTargetSdkVersion by extra(33)
val androidMinSdkVersion by extra(24)
val androidCompileSdkVersion by extra(32)
val androidBuildToolsVersion by extra("32.0.0")
val androidCompileNdkVersion = Version.getNdkVersion()

fun Project.configureBaseExtension() {
    extensions.findByType(BaseExtension::class)?.run {
        compileSdkVersion(androidCompileSdkVersion)
        ndkVersion = androidCompileNdkVersion
        buildToolsVersion = androidBuildToolsVersion

        defaultConfig {
            minSdk = androidMinSdkVersion
            targetSdk = androidTargetSdkVersion
            versionCode = verCode
            versionName = verName
        }

        compileOptions {
            sourceCompatibility = Version.java
            targetCompatibility = Version.java
        }

        packagingOptions.jniLibs.useLegacyPackaging = false
    }
}

subprojects {
    plugins.withId("com.android.application") {
        configureBaseExtension()
    }
    plugins.withId("com.android.library") {
        configureBaseExtension()
    }
}