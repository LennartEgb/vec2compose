package svg

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
        val fillOpacity: Float = 1.0f
    )
}
