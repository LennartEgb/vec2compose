package dev.lennartegb.vec2compose.core

import dev.lennartegb.vec2compose.core.imagevector.ImageVectorCreator
import kotlin.test.Test
import kotlin.test.assertEquals

class KotlinFileContentCreatorTest {
    @Test
    fun `create file content for ImageVector`() {
        val indentation = "    "
        val creator = KotlinFileContentCreator(
            importProvider = { "IMPORTS" },
            imageVectorCreator = ImageVectorCreator(indentation),
            indentation = indentation
        )
        val actual = creator.create(
            name = "Home",
            packageName = "com.example.icons",
            imageVector = ImageVector(
                width = 24,
                height = 25,
                viewportWidth = 26f,
                viewportHeight = 27f,
                nodes = emptyList()
            )
        )
        assertEquals(
            actual = actual,
            expected = """
                package com.example.icons
                
                IMPORTS
                
                private var cache: ImageVector? = null
                val Home: ImageVector
                    get() = cache ?: ImageVector.Builder(
                        name = "Home",
                        defaultWidth = 24.dp,
                        defaultHeight = 25.dp,
                        viewportWidth = 26.0f,
                        viewportHeight = 27.0f
                    ).build().also { cache = it }
                
            """.trimIndent()
        )
    }

    @Test
    fun `create file content for ImageVector without package`() {
        val indentation = "    "
        val creator = KotlinFileContentCreator(
            importProvider = { "IMPORTS" },
            imageVectorCreator = ImageVectorCreator(indentation),
            indentation = indentation
        )
        val actual = creator.create(
            name = "Home",
            packageName = null,
            imageVector = ImageVector(
                width = 24,
                height = 25,
                viewportWidth = 26f,
                viewportHeight = 27f,
                nodes = emptyList()
            )
        )
        assertEquals(
            actual = actual,
            expected = """
                IMPORTS
                
                private var cache: ImageVector? = null
                val Home: ImageVector
                    get() = cache ?: ImageVector.Builder(
                        name = "Home",
                        defaultWidth = 24.dp,
                        defaultHeight = 25.dp,
                        viewportWidth = 26.0f,
                        viewportHeight = 27.0f
                    ).build().also { cache = it }
                
            """.trimIndent()
        )
    }
}
