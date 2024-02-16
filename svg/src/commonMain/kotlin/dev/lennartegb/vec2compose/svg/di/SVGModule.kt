package dev.lennartegb.vec2compose.svg.di

import dev.lennartegb.vec2compose.core.HexColorParser
import dev.lennartegb.vec2compose.core.VectorSetParser
import dev.lennartegb.vec2compose.core.commands.CommandParser
import dev.lennartegb.vec2compose.core.commands.PathParser
import dev.lennartegb.vec2compose.svg.KeywordColorParser
import dev.lennartegb.vec2compose.svg.SVGColorParser
import dev.lennartegb.vec2compose.svg.SVGDeserializer
import dev.lennartegb.vec2compose.svg.SVGParser
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val svgQualifier = named("svg")
val svgModule = module {
    factory<VectorSetParser>(qualifier = svgQualifier) {
        SVGParser(colorParser = get(), pathParser = get(), deserializer = get())
    }
    factoryOf(::PathParser)
    factoryOf(::CommandParser)
    factoryOf(::SVGDeserializer)
    factoryOf(::SVGColorParser)
    factoryOf(::HexColorParser)
    factoryOf(::KeywordColorParser)
}
