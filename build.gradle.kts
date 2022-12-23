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

allprojects {
  repositories {
    mavenCentral()
  }

  java {                                      
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
}

subprojects {
  apply(plugin = "me.champeau.jmh")

  jmh {
    iterations.set(10)
    warmupIterations.set(3)
    threads.set(Runtime.getRuntime().availableProcessors())
    failOnError.set(true)
    jmhVersion.set("1.36")
  }
}
