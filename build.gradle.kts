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

subprojects {
  apply(plugin = "me.champeau.jmh")

  jmh {
    fork.set(2)
    iterations.set(5)
    warmupIterations.set(2)
    threads.set(32)
    failOnError.set(true)
    jmhVersion.set("1.36")
  }
}
