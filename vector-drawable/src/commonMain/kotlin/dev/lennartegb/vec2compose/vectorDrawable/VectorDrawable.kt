package dev.lennartegb.vec2compose.vectorDrawable

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlPolyChildren
import nl.adaptivity.xmlutil.serialization.XmlSerialName

@Suppress("ktlint")
private const val androidNamespace = "http://schemas.android.com/apk/res/android"

@XmlSerialName("vector")
@Serializable
internal data class VectorDrawable(
    @XmlSerialName("width", androidNamespace) val widthInDp: String,
    @XmlSerialName("height", androidNamespace) val heightInDp: String,
    @XmlSerialName("viewportWidth", androidNamespace) val viewportWidth: Float,
    @XmlSerialName("viewportHeight", androidNamespace) val viewportHeight: Float,
    @XmlSerialName("tint", androidNamespace) val tint: String? = null,

    @XmlPolyChildren([GROUP, PATH])
    val children: List<@Polymorphic Child>
) {
    companion object {
        const val GROUP = "group"
        const val PATH = "path"
    }

    sealed interface Child

    @Serializable
    @SerialName(GROUP)
    @XmlSerialName(GROUP)
    data class Group(
        @XmlSerialName("name", androidNamespace) val name: String? = null,
        @XmlSerialName("pivotX", androidNamespace) val pivotX: Float? = null,
        @XmlSerialName("pivotY", androidNamespace) val pivotY: Float? = null,
        @XmlSerialName("translateX", androidNamespace) val translateX: Float? = null,
        @XmlSerialName("translateY", androidNamespace) val translateY: Float? = null,
        @XmlSerialName("scaleX", androidNamespace) val scaleX: Float? = null,
        @XmlSerialName("scaleY", androidNamespace) val scaleY: Float? = null,
        @XmlSerialName("rotation", androidNamespace) val rotation: Float? = null,

        @XmlPolyChildren([GROUP, PATH])
        val children: List<@Polymorphic Child>
    ) : Child

    @Serializable
    @SerialName(PATH)
    @XmlSerialName(PATH)
    data class Path(
        @XmlSerialName("name", androidNamespace) val name: String? = null,
        @XmlSerialName("fillType", androidNamespace) val fillType: String = "nonZero",
        @XmlSerialName("fillColor", androidNamespace) val fillColor: String? = null,
        @XmlSerialName("pathData", androidNamespace) val pathData: String,
        @XmlSerialName("alpha", androidNamespace) val alpha: Float = 1.0f,
        @XmlSerialName("strokeWidth", androidNamespace) val strokeWidth: String? = null,
        @XmlSerialName("strokeLineCap", androidNamespace) val strokeLineCap: String? = null,
        @XmlSerialName("strokeLineJoin", androidNamespace) val strokeLineJoin: String? = null,
        @XmlSerialName("strokeColor", androidNamespace) val strokeColor: String? = null,
        @XmlSerialName("strokeAlpha", androidNamespace) val strokeAlpha: String? = null,
        @XmlSerialName("strokeMiterLimit", androidNamespace) val strokeMiterLimit: String? = null
    ) : Child
}
