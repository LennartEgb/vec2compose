@file:OptIn(ExperimentalComposeUiApi::class)

package dev.lennartegb.vec2compose.app.data

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.ExternalDragValue

data class File(val name: String, val content: String)

expect fun ExternalDragValue.toFiles(): List<File>