package androidvector

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

class AndroidVectorSerializer {
    private val mapper: ObjectMapper = XmlMapper(JacksonXmlModule()).registerKotlinModule()

    fun serialize(xml: XML): Result<AndroidVector> {
        return runCatching { mapper.readValue(xml.content, AndroidVector::class.java) }
    }
}