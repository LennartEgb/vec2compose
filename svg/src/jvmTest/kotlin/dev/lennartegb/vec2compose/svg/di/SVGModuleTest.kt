package dev.lennartegb.vec2compose.svg.di

import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.verify.verify

class SVGModuleTest {
    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun verify_SVGModule() {
        svgModule.verify()
    }
}
