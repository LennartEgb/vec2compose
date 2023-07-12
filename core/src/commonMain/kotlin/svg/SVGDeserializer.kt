package svg

import nl.adaptivity.xmlutil.ExperimentalXmlUtilApi
import nl.adaptivity.xmlutil.serialization.DefaultXmlSerializationPolicy
import nl.adaptivity.xmlutil.serialization.XML

internal class SVGDeserializer {

    @OptIn(ExperimentalXmlUtilApi::class)
    private val xmlConfig = XML {
        policy = DefaultXmlSerializationPolicy(
            pedantic = false,
            unknownChildHandler = { _, _, _, _, _ -> emptyList() }
        )
        this.repairNamespaces
    }

    fun deserialize(content: String): Result<SVG> = runCatching {
        xmlConfig.decodeFromString(deserializer = SVG.serializer(), string = content)
    }
}
