package androidvector

import com.fasterxml.jackson.databind.ObjectMapper

internal class AndroidVectorSerializer(private val mapper: ObjectMapper) {
    fun serialize(xml: XML): Result<AndroidVector> {
        return runCatching { mapper.readValue(xml.content, AndroidVector::class.java) }
    }
}