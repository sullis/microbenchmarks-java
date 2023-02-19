
package mbjava

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

class MbUtils {
    companion object {
        private val mapper = jacksonObjectMapper()

        fun benchmarksJson(): String {
            val files = Files.walk(Path.of("."))
                .filter(Files::isRegularFile)
                .map { it.toFile() }
                .filter { it.name.endsWith("Benchmark.java") }
                .map { it.nameWithoutExtension }
                .sorted()
                .toList()
            return mapper.writeValueAsString(files);
        }
    }
}