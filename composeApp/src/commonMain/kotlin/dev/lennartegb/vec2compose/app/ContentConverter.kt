package dev.lennartegb.vec2compose.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.lennartegb.vec2compose.app.data.File

fun interface ContentConverter : (File) -> Result<String>

@Composable
fun rememberContentConverter(
    imageVectorCreator: ImageVectorCreator = rememberImageVectorCreator(),
    indentation: String = " ".repeat(4)
): ContentConverter = remember {
    ContentConverter { file ->
        imageVectorCreator(file).mapCatching {
            dev.lennartegb.vec2compose.core.imagevector.ImageVectorCreator(indentation = indentation)
                .create(name = file.name, imageVector = it)
        }
    }
}
