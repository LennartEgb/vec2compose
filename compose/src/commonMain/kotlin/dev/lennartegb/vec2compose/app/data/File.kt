@file:OptIn(ExperimentalComposeUiApi::class)

package dev.lennartegb.vec2compose.app.data

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.ExternalDragValue

data class File(val name: String, val path: String)

expect fun ExternalDragValue.toFiles(): List<File>
expect val File.content: String