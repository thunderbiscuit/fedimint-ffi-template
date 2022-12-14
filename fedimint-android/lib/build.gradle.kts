plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android") version "1.6.10"
    id("maven-publish")
    id("signing")

    // Custom plugin to generate the native libs and bindings file
    id("org.fedimint.plugins.generate-android-bindings")
}

repositories {
    mavenCentral()
    google()
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 21
        targetSdk = 31
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(file("proguard-android-optimize.txt"), file("proguard-rules.pro"))
        }
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    implementation("net.java.dev.jna:jna:5.8.0@aar")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7")
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("androidx.core:core-ktx:1.7.0")
    api("org.slf4j:slf4j-api:1.7.30")

    androidTestImplementation("com.github.tony19:logback-android:2.0.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.1")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = "org.fedimint"
                artifactId = "fedimint-android"
                version = "0.1.0"

                from(components["release"])
                pom {
                    name.set("fedimint-android")
                    description.set("Fedimint Kotlin language bindings.")
                    url.set("https://fedimint.org")
                    licenses {
                        license {
                            name.set("APACHE 2.0")
                            url.set("")
                        }
                    }
                    // developers {
                    //     developer {
                    //         id.set("")
                    //         name.set("")
                    //         email.set("")
                    //     }
                    // }
                    // scm {
                    //     connection.set("")
                    //     developerConnection.set("")
                    //     url.set("")
                    // }
                }
            }
        }
    }
}

// signing {
//     val signingKeyId: String? by project
//     val signingKey: String? by project
//     val signingPassword: String? by project
//     useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)
//     sign(publishing.publications)
// }

// plugins {
//     id("org.jetbrains.kotlin.jvm") version "1.7.20"
//     `java-library`
// }
//
// repositories {
//     // Use Maven Central for resolving dependencies.
//     mavenCentral()
// }
//
// dependencies {
//     // Align versions of all Kotlin components
//     implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
//
//     // Use the Kotlin JDK 8 standard library.
//     implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
//
//     // This dependency is used internally, and not exposed to consumers on their own compile classpath.
//     implementation("com.google.guava:guava:30.1.1-jre")
//
//     // This dependency is exported to consumers, that is to say found on their compile classpath.
//     api("org.apache.commons:commons-math3:3.6.1")
// }
//
// testing {
//     suites {
//         // Configure the built-in test suite
//         val test by getting(JvmTestSuite::class) {
//             // Use Kotlin Test test framework
//             useKotlinTest()
//         }
//     }
// }
