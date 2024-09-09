package dev.lennartegb.vec2compose.vectorDrawable

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import nl.adaptivity.xmlutil.ExperimentalXmlUtilApi
import nl.adaptivity.xmlutil.serialization.UnknownChildHandler
import nl.adaptivity.xmlutil.serialization.XML

internal class VectorDrawableDeserializer {
    private val baseModule = SerializersModule {
        polymorphic(VectorDrawable.Child::class) {
            subclass(VectorDrawable.Group::class)
            subclass(VectorDrawable.Path::class)
        }
    }

    @OptIn(ExperimentalXmlUtilApi::class)
    private val xmlConfig = XML(baseModule) {
        autoPolymorphic = true
        defaultPolicy {
            pedantic = false
            repairNamespaces = true
            unknownChildHandler = UnknownChildHandler { _, _, _, _, _ -> emptyList() }
        }
    }

    fun deserialize(content: String): VectorDrawable = xmlConfig.decodeFromString(content)
}
