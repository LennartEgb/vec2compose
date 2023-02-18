package utils

import java.io.File
import kotlin.test.fail

internal class FileLoader {
  fun load(name: String): File {
    return javaClass.classLoader.getResource(name)?.file?.let { File(it) }
        ?: fail("File $name not found")
  }
}
