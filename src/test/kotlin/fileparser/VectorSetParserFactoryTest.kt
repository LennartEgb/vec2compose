package fileparser

import VectorSetParserFactory
import java.io.File
import kotlin.test.assertIs
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import svg.SVGParser
import vectordrawable.VectorDrawableParser

internal class VectorSetParserFactoryTest {

  @Test
  fun `createFileParser with xml file returns XMLFileParser`() {
    val factory = VectorSetParserFactory(File("file.xml"))
    assertIs<VectorDrawableParser>(factory.create())
  }

  @Test
  fun `createFileParser with svg file returns SVGFileParser`() {
    val factory = VectorSetParserFactory(File("file.svg"))
    assertIs<SVGParser>(factory.create())
  }

  @Test
  fun `createFileParser with unsupported file throws IllegalArgumentException`() {
    val factory = VectorSetParserFactory(File("file.jpeg"))
    assertThrows<IllegalArgumentException> { factory.create() }
  }
}
