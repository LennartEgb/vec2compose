package svg

import androidvector.XML
import com.fasterxml.jackson.databind.ObjectMapper

internal class SVGDeserializer(private val mapper: ObjectMapper) {
    fun deserialize(xml: XML): Result<SVG> {
        return runCatching { mapper.readValue(xml.content, SVG::class.java) }
    }
}