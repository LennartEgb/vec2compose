package vectordrawable

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

@XmlSerialName(value = "vector", namespace = "http://schemas.android.com/apk/res/android", prefix = "")
@Serializable
internal data class VectorDrawable(
    val widthInDp: String,
    val heightInDp: String,
    val viewportWidth: Float,
    val viewportHeight: Float,
    val tint: String? = null,
    val path: List<Path> = emptyList(),
    val group: List<Group> = emptyList()
) {

    @XmlSerialName(value = "group")
    @Serializable
    data class Group(
        val name: String? = null,
        val pivotX: Float? = null,
        val pivotY: Float? = null,
        val translateX: Float? = null,
        val translateY: Float? = null,
        val scaleX: Float? = null,
        val scaleY: Float? = null,
        val rotation: Float? = null,
        @XmlElement(value = true)
        val group: List<Group> = emptyList(),
        @XmlElement(value = true)
        val path: List<Path> = emptyList()
    )

    @XmlSerialName(value = "path")
    @Serializable
    data class Path(
        val name: String? = null,
        val fillType: String = "nonZero",
        val fillColor: String? = null,
        val pathData: String,
        val alpha: Float = 1.0f
    )
}
