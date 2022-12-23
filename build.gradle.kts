plugins {
    `java`
    id("me.champeau.jmh") version "0.6.8"
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter("5.9.1")
        }
    }
}

java {                                      
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
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
    threads.set(Runtime.getRuntime().availableProcessors())
    failOnError.set(true)
    jmhVersion.set("1.36")
  }
}
