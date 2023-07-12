package vectordrawable

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

private const val androidNamespace = "http://schemas.android.com/apk/res/android"

@XmlSerialName(value = "vector")
@Serializable
internal data class VectorDrawable(
    @XmlSerialName(namespace = androidNamespace, value = "width")
    val widthInDp: String,
    @XmlSerialName(namespace = androidNamespace, value = "height")
    val heightInDp: String,
    @XmlSerialName(namespace = androidNamespace, value = "viewportWidth")
    val viewportWidth: Float,
    @XmlSerialName(namespace = androidNamespace, value = "viewportHeight")
    val viewportHeight: Float,
    @XmlSerialName(namespace = androidNamespace, value = "tint")
    val tint: String? = null,
    val path: List<Path> = emptyList(),
    val group: List<Group> = emptyList()
) {

    @XmlSerialName(value = "group")
    @Serializable
    data class Group(
        @XmlSerialName(namespace = androidNamespace, value = "name")
        val name: String? = null,
        @XmlSerialName(namespace = androidNamespace, value = "pivotX")
        val pivotX: Float? = null,
        @XmlSerialName(namespace = androidNamespace, value = "pivotY")
        val pivotY: Float? = null,
        @XmlSerialName(namespace = androidNamespace, value = "translateX")
        val translateX: Float? = null,
        @XmlSerialName(namespace = androidNamespace, value = "translateY")
        val translateY: Float? = null,
        @XmlSerialName(namespace = androidNamespace, value = "scaleX")
        val scaleX: Float? = null,
        @XmlSerialName(namespace = androidNamespace, value = "scaleY")
        val scaleY: Float? = null,
        @XmlSerialName(namespace = androidNamespace, value = "rotation")
        val rotation: Float? = null,
        @XmlElement(value = true)
        val group: List<Group> = emptyList(),
        @XmlElement(value = true)
        val path: List<Path> = emptyList()
    )

    @XmlSerialName(value = "path")
    @Serializable
    data class Path(
        @XmlSerialName(namespace = androidNamespace, value = "name")
        val name: String? = null,
        @XmlSerialName(namespace = androidNamespace, value = "fillType")
        val fillType: String = "nonZero",
        @XmlSerialName(namespace = androidNamespace, value = "fillColor")
        val fillColor: String? = null,
        @XmlSerialName(namespace = androidNamespace, value = "pathData")
        val pathData: String,
        @XmlSerialName(namespace = androidNamespace, value = "alpha")
        val alpha: Float = 1.0f
    )
}
