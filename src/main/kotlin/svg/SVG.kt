package svg

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement(localName = "svg")
data class SVG(
    @field:JacksonXmlProperty(localName = "width")
    val width: String,
    @field:JacksonXmlProperty(localName = "height")
    val height: String,
    @field:JacksonXmlProperty(localName = "viewBox")
    val viewBox: String? = null,
    @field:JacksonXmlProperty(localName = "fill")
    val fill: String? = null,
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val path: List<Path> = emptyList(),
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val g: List<Group> = emptyList()
) {
    data class Group(
        @field:JacksonXmlProperty(localName = "id")
        val name: String? = null,
        @field:JacksonXmlElementWrapper(useWrapping = false)
        val g: List<Group> = emptyList(),
        @field:JacksonXmlElementWrapper(useWrapping = false)
        val path: List<Path> = emptyList(),
    )

    data class Path(
        @field:JacksonXmlProperty(localName = "id")
        val id: String = "",
        @field:JacksonXmlProperty(localName = "d")
        val pathData: String,
        @field:JacksonXmlProperty(localName = "fill-rule")
        val fillRule: String = "nonzero",
        @field:JacksonXmlProperty(localName = "fill")
        val fill: String? = null,
    )
}
