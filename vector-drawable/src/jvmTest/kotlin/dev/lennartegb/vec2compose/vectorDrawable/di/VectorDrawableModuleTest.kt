package dev.lennartegb.vec2compose.vectorDrawable.di

import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.verify.verify

class VectorDrawableModuleTest {
    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun verify_vectorDrawableModule() {
        vectorDrawableModule.verify()
    }
}
