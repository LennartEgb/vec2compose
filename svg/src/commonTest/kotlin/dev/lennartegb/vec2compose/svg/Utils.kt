package dev.lennartegb.vec2compose.svg

internal fun testSVG(
    height: String = "24px",
    viewBox: String = "0 0 24 24",
    width: String = "24px",
    fill: String = "#000000",
    children: List<SVG.Child>
) = SVG(
    width = width,
    height = height,
    viewBox = viewBox,
    fill = fill,
    children = children
)

internal inline fun svg(
    height: String = "24px",
    viewBox: String = "0 0 24 24",
    width: String = "24px",
    fill: String = "#000000",
    block: () -> String
): String {
    return buildString {
        // NOTE: The supported SVGs must have the documented namespace: https://www.w3.org/TR/SVG/struct.html#Namespace
        appendLine(
            """
                <svg xmlns="http://www.w3.org/2000/svg"
                     height="$height"
                     viewBox="$viewBox"
                     width="$width"
                     fill="$fill">
            """.trimIndent()
        )
        appendLine(block())
        appendLine("</svg>")
    }
}
