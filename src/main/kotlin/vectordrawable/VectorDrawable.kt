package vectordrawable

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement(localName = "vector")
internal data class VectorDrawable(
    @field:JacksonXmlProperty(localName = "width")
    val widthInDp: String,
    @field:JacksonXmlProperty(localName = "height")
    val heightInDp: String,
    @field:JacksonXmlProperty(localName = "viewportWidth")
    val viewportWidth: Int,
    @field:JacksonXmlProperty(localName = "viewportHeight")
    val viewportHeight: Int,
    @field:JacksonXmlProperty(localName = "tint")
    val tint: String? = null,
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val path: List<Path> = emptyList()
) {
    data class Path(
        @field:JacksonXmlProperty(localName = "fillType")
        val fillType: String = "nonZero",
        @field:JacksonXmlProperty(localName = "fillColor")
        val fillColor: String? = null,
        @field:JacksonXmlProperty(localName = "pathData")
        val pathData: String,
    )
}