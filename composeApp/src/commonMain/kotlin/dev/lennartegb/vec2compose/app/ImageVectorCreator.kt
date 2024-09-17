package dev.lennartegb.vec2compose.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.lennartegb.vec2compose.app.data.File
import dev.lennartegb.vec2compose.svg.svgImageVectorParser
import dev.lennartegb.vec2compose.vectorDrawable.xmlImageVectorParser

fun interface ImageVectorCreator : (File) -> Result<dev.lennartegb.vec2compose.core.ImageVector>

@Composable
internal fun rememberImageVectorCreator(): ImageVectorCreator = remember {
    ImageVectorCreator {
        when (it.extension) {
            "xml" -> xmlImageVectorParser()
            "svg" -> svgImageVectorParser()
            else -> error("Only XML and SVG files are allowed")
        }.parse(it.content)
    }
}
