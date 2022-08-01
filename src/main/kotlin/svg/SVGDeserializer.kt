package svg

import androidvector.XML
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

internal class SVGDeserializer {
    private val mapper = XmlMapper(JacksonXmlModule()).registerKotlinModule()

    fun deserialize(xml: XML): Result<SVG> {
        return runCatching { mapper.readValue(xml.content, SVG::class.java) }
    }
}