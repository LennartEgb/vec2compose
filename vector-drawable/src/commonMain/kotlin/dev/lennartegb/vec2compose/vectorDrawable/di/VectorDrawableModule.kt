package dev.lennartegb.vec2compose.vectorDrawable.di

import dev.lennartegb.vec2compose.core.HexColorParser
import dev.lennartegb.vec2compose.core.VectorSetParser
import dev.lennartegb.vec2compose.core.commands.CommandParser
import dev.lennartegb.vec2compose.core.commands.PathParser
import dev.lennartegb.vec2compose.vectorDrawable.VectorDrawableDeserializer
import dev.lennartegb.vec2compose.vectorDrawable.VectorDrawableParser
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val vectorDrawableQualifier = named("vectorDrawable")
val vectorDrawableModule = module {
    factory<VectorSetParser>(qualifier = vectorDrawableQualifier) {
        VectorDrawableParser(pathParser = get(), colorParser = get(), deserializer = get())
    }

    factoryOf(::HexColorParser)
    factoryOf(::PathParser)
    factoryOf(::CommandParser)
    factoryOf(::VectorDrawableDeserializer)
}
