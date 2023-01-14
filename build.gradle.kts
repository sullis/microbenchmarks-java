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
    threads.set(32)
    failOnError.set(true)
    jmhVersion.set("1.36")
    // Chronicle-Core needs special JVM args
    // see: https://chronicle.software/chronicle-support-java-17
    jvmArgsAppend.set(listOf(
        "--add-exports=java.base/jdk.internal.ref=ALL-UNNAMED",
        "--add-exports=java.base/sun.nio.ch=ALL-UNNAMED",
        "--add-exports=jdk.unsupported/sun.misc=ALL-UNNAMED",
        "--add-exports=jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED",
        "--add-opens=jdk.compiler/com.sun.tools.javac=ALL-UNNAMED",
        "--add-opens=java.base/java.lang=ALL-UNNAMED",
        "--add-opens=java.base/java.lang.reflect=ALL-UNNAMED",
        "--add-opens=java.base/java.io=ALL-UNNAMED",
        "--add-opens=java.base/java.util=ALL-UNNAMED"))
  }
}
