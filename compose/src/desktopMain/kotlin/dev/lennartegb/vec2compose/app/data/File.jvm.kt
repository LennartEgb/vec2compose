@file:OptIn(ExperimentalComposeUiApi::class)

package dev.lennartegb.vec2compose.app.data

import androidx.compose.ui.DragData
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.ExternalDragValue
import java.net.URI

actual fun ExternalDragValue.toFiles(): List<File> {
    val data = dragData
    if (data is DragData.FilesList) {
        return data.readFiles().map(::readFiles)
    }
    return emptyList()
}

private fun readFiles(path: String): File {
    val javaFile = java.io.File(URI.create(path))
    return File(name = javaFile.name, content = javaFile.readText())
}
