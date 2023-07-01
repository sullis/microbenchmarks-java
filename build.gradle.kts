import mbjava.MbUtils

plugins {
    `java`
    id("me.champeau.jmh") version "0.7.1"
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter("5.9.2")
        }
    }
}

java {                                      
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
        vendor.set(JvmVendorSpec.AZUL)
    }
}

tasks.register("benchmarkJson") {
    val json = MbUtils.benchmarksJson()
    System.out.println(json);
}

allprojects {
  repositories {
    mavenCentral()
    maven {
        url = uri("https://artifacts-oss.netflix.net/artifactory/maven-oss-snapshots")
        mavenContent {
            includeGroup("com.netflix.zuul")
            snapshotsOnly()
        }
    }
  }
}

val benchmarkName = providers.systemProperty("benchmark").getOrElse("")

jmh {
    fork.set(2)
    includes.set(listOf(benchmarkName))
    iterations.set(5)
    warmupIterations.set(2)
    failOnError.set(true)
    jmhVersion.set("1.36")
}

val brotli4jVersion = "1.12.0"
val nettyVersion = "4.1.94.Final"
val netty5Version = "5.0.0.Alpha5"
val zuulOssVersion = "2.3.1-SNAPSHOT"

dependencies {
    jmh("org.springframework:spring-web:6.0.9")
    jmh("com.fasterxml.jackson.core:jackson-databind:2.14.3")
    jmh("com.google.guava:guava:31.1-jre")
    jmh("com.github.ben-manes.caffeine:caffeine:3.1.2")
    jmh("org.apache.commons:commons-lang3:3.12.0")
    jmh("com.netflix.zuul:zuul-core:$zuulOssVersion")
    jmh("io.netty:netty-codec-http:$nettyVersion")
    jmh("io.netty:netty-codec-http2:$nettyVersion")
    jmh("io.netty:netty-common:$nettyVersion")
    jmh("io.netty:netty5-buffer:$netty5Version")
    jmh("io.netty:netty5-common:$netty5Version")
    jmh("org.apache.logging.log4j:log4j-core:2.20.0")
    jmh("org.slf4j:slf4j-api:1.7.36")
    jmh("com.netflix.netflix-commons:netflix-commons-util:0.3.0")
    jmh("com.fasterxml.uuid:java-uuid-generator:4.0.1")
    jmh("com.datastax.oss:java-driver-core:4.15.0")
    jmh("com.aayushatharva.brotli4j:brotli4j:$brotli4jVersion")
    jmh("com.aayushatharva.brotli4j:native-linux-x86_64:$brotli4jVersion")
    jmh("com.aayushatharva.brotli4j:native-linux-aarch64:$brotli4jVersion")
    jmh("com.aayushatharva.brotli4j:native-osx-x86_64:$brotli4jVersion")
    jmh("com.aayushatharva.brotli4j:native-osx-aarch64:$brotli4jVersion")
}
