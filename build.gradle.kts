import mbjava.MbUtils

plugins {
    `java`
    id("me.champeau.jmh") version "0.7.1"
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter("5.10.3")
        }
    }
}

java {                                      
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
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
    jmhVersion.set("1.37")
    profilers.set(listOf("gc"))
}

val brotli4jVersion = "1.16.0"
val nettyVersion = "4.1.111.Final"
val netty5Version = "5.0.0.Alpha5"
val zuulOssVersion = "2.5.0"
val log4jVersion = "2.23.1"

dependencies {
    jmh("io.projectreactor.netty:reactor-netty-http:1.1.20")
    jmh("org.springframework:spring-web:6.1.10")
    jmh("com.fasterxml.jackson.core:jackson-databind:2.17.2")
    jmh("com.google.guava:guava:33.1.0-jre")
    jmh("com.github.ben-manes.caffeine:caffeine:3.1.8")
    jmh("org.apache.commons:commons-lang3:3.12.0")
    jmh("com.netflix.zuul:zuul-core:$zuulOssVersion")
    jmh("io.netty:netty-codec-http:$nettyVersion")
    jmh("io.netty:netty-codec-http2:$nettyVersion")
    jmh("io.netty:netty-transport-native-epoll:$nettyVersion:linux-x86_64")
    jmh("io.netty:netty-transport-native-epoll:$nettyVersion:linux-aarch_64")
    jmh("io.netty:netty-common:$nettyVersion")
    jmh("io.netty:netty5-buffer:$netty5Version")
    jmh("io.netty:netty5-common:$netty5Version")
    jmh("org.apache.logging.log4j:log4j-core:$log4jVersion")
    jmh("org.slf4j:slf4j-api:2.0.13")
    jmh("com.netflix.netflix-commons:netflix-commons-util:0.3.0")
    jmh("com.fasterxml.uuid:java-uuid-generator:5.1.0")
    jmh("com.datastax.oss:java-driver-core:4.17.0")
    jmh("com.github.luben:zstd-jni:1.5.6-3")
    jmh("com.aayushatharva.brotli4j:brotli4j:$brotli4jVersion")
    jmh("com.aayushatharva.brotli4j:native-linux-x86_64:$brotli4jVersion")
    jmh("com.aayushatharva.brotli4j:native-linux-aarch64:$brotli4jVersion")
    jmh("com.aayushatharva.brotli4j:native-osx-x86_64:$brotli4jVersion")
    jmh("com.aayushatharva.brotli4j:native-osx-aarch64:$brotli4jVersion")
}
