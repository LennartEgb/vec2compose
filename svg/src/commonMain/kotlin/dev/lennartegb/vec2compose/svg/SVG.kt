package dev.lennartegb.vec2compose.svg

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlPolyChildren
import nl.adaptivity.xmlutil.serialization.XmlSerialName

private const val svgNamespace = "http://www.w3.org/2000/svg"

@Serializable
@SerialName("svg")
@XmlSerialName("svg", namespace = svgNamespace)
data class SVG(
    @SerialName("width") val width: String,
    @SerialName("height") val height: String,
    @SerialName("viewBox") val viewBox: String? = null,
    @SerialName("fill") val fill: String? = null,
    @XmlPolyChildren(["g", "path", "circle"])
    val children: List<@Polymorphic Child> = emptyList(),
) {

    sealed interface Child

    @Serializable
    @SerialName("g")
    @XmlSerialName("g")
    data class Group(
        @SerialName("id") val name: String? = null,
        @SerialName("transform") val transform: String? = null,
        @XmlPolyChildren(["g", "path", "circle"])
        val children: List<@Polymorphic Child> = emptyList(),
    ) : Child

    @Serializable
    @SerialName("path")
    @XmlSerialName("path")
    data class Path(
        @SerialName("id") val id: String = "",
        @SerialName("d") val pathData: String,
        @SerialName("fill-rule") val fillRule: String = "nonzero",
        @SerialName("fill") val fill: String? = null,
        @SerialName("fill-opacity") val fillOpacity: Float = 1.0f,
        @SerialName("stroke") val strokeColor: String? = null,
        @SerialName("stroke-width") val strokeWidth: String? = null,
        @SerialName("stroke-linecap") val strokeLinecap: String? = null,
        @SerialName("stroke-linejoin") val strokeLinejoin: String? = null,
        @SerialName("stroke-opacity") val strokeAlpha: String? = null,
        @SerialName("stroke-miterlimit") val strokeMiter: String? = null,
    ) : Child

    @Serializable
    @SerialName("circle")
    @XmlSerialName("circle")
    data class Circle(
        @SerialName("cx") val centerX: String,
        @SerialName("cy") val centerY: String,
        @SerialName("r") val radius: String,
        @SerialName("id") val id: String? = null,
        @SerialName("fill") val fill: String? = null,
        @SerialName("stroke") val stroke: String? = null,
        @SerialName("stroke-width") val strokeWidth: String? = null,
        @SerialName("opacity") val opacity: String = "1",
    ) : Child
}
