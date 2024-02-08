package dev.lennartegb.vec2compose.svg

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlSerialName

private const val svgNamespace = "http://www.w3.org/2000/svg"

@XmlSerialName(value = "svg", namespace = svgNamespace)
@Serializable
data class SVG(
    @SerialName(value = "width")
    val width: String,
    @SerialName(value = "height")
    val height: String,
    @SerialName(value = "viewBox")
    val viewBox: String? = null,
    @SerialName(value = "fill")
    val fill: String? = null,
    @SerialName(value = "path")
    val path: List<Path> = emptyList(),
    @SerialName(value = "g")
    val g: List<Group> = emptyList()
) {

    @XmlSerialName(value = "g")
    @Serializable
    data class Group(
        @SerialName(value = "id")
        val name: String? = null,
        @SerialName(value = "g")
        val g: List<Group> = emptyList(),
        @SerialName(value = "path")
        val path: List<Path> = emptyList(),
        @SerialName(value = "transform")
        val transform: String? = null
    )

    @XmlSerialName(value = "path")
    @Serializable
    data class Path(
        @SerialName(value = "id")
        val id: String = "",
        @SerialName(value = "d")
        val pathData: String,
        @SerialName(value = "fill-rule")
        val fillRule: String = "nonzero",
        @SerialName(value = "fill")
        val fill: String? = null,
        @SerialName(value = "fill-opacity")
        val fillOpacity: Float = 1.0f,
        @SerialName(value = "stroke")
        val strokeColor: String? = null,
        @SerialName(value = "stroke-width")
        val strokeWidth: String? = null,
        @SerialName(value = "stroke-linecap")
        val strokeLinecap: String? = null,
        @SerialName(value = "stroke-linejoin")
        val strokeLinejoin: String? = null,
        @SerialName(value = "stroke-opacity")
        val strokeAlpha: String? = null,
        @SerialName(value = "stroke-miterlimit")
        val strokeMiter: String? = null,
    )
}
