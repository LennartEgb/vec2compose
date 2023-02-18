package svg

import com.fasterxml.jackson.databind.ObjectMapper

internal class SVGDeserializer(private val mapper: ObjectMapper) {
  fun deserialize(content: String): Result<SVG> {
    return runCatching { mapper.readValue(content, SVG::class.java) }
  }
}
