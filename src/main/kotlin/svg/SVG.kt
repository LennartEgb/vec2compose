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
    val viewBox: String,
    @field:JacksonXmlProperty(localName = "fill")
    val fill: String,
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val path: List<Path>
) {
    data class Path(
        @field:JacksonXmlProperty(localName = "d")
        val pathData: String,
        @field:JacksonXmlProperty(localName = "fill")
        val fill: String? = null,
    )
}
