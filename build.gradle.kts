import mbjava.MbUtils

plugins {
    `java`
    id("me.champeau.jmh") version "0.6.8"
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
  }
}

jmh {
    fork.set(2)
    includes.set(listOf(providers.systemProperty("microbenchmark").get()))
    iterations.set(5)
    warmupIterations.set(2)
    threads.set(32)
    failOnError.set(true)
    jmhVersion.set("1.36")
}

dependencies {
    jmh("com.fasterxml.jackson.core:jackson-databind:2.14.2")
    jmh("com.google.guava:guava:31.1-jre")
    jmh("com.github.ben-manes.caffeine:caffeine:3.1.2")
    jmh("org.apache.commons:commons-lang3:3.12.0")
    jmh("com.netflix.zuul:zuul-core:2.3.0")
    jmh("io.netty:netty-codec-http:4.1.89.Final")
    jmh("io.netty:netty-codec-http2:4.1.89.Final")
    jmh("io.netty:netty-common:4.1.89.Final")
    jmh("org.apache.logging.log4j:log4j-core:2.19.0")
    jmh("org.slf4j:slf4j-api:1.7.36")
    jmh("com.netflix.netflix-commons:netflix-commons-util:0.3.0")
    jmh("com.fasterxml.uuid:java-uuid-generator:4.0.1")
    jmh("com.datastax.oss:java-driver-core:4.15.0")
}
