package di

import dev.lennartegb.vec2compose.core.VectorSetParser
import dev.lennartegb.vec2compose.core.imagevector.ImageVectorCreator
import dev.lennartegb.vec2compose.core.imagevector.ImageVectorImportProvider
import dev.lennartegb.vec2compose.svg.di.svgModule
import dev.lennartegb.vec2compose.svg.di.svgQualifier
import dev.lennartegb.vec2compose.vectorDrawable.di.vectorDrawableModule
import dev.lennartegb.vec2compose.vectorDrawable.di.vectorDrawableQualifier
import file.FileReader
import okio.FileSystem
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.module.dsl.factoryOf
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import output.FileOutputStrategy
import output.OutputStrategy

val cliModule = module {
    includes(svgModule, vectorDrawableModule)
    factoryOf(::FileReader)
    factoryOf(::ImageVectorImportProvider)
    factory<FileSystem> { FileSystem.SYSTEM }

    factory<OutputStrategy> { params ->
        val path: String? = params[0]
        val name: String = params[1]
        val indentation: String = params[2]

        if (path == null) {
            OutputStrategy(::println)
        } else {
            FileOutputStrategy(
                name = name,
                pathname = path,
                importProvider = get(),
                fileSystem = get(),
                indentation = indentation
            )
        }
    }

    factory<VectorSetParser> { params ->
        when (val extension = params.get<String>()) {
            "svg" -> get(qualifier = svgQualifier)
            "xml" -> get(qualifier = vectorDrawableQualifier)
            else -> error("Unsupported file with extension: $extension")
        }
    }

    factory<ImageVectorCreator> { params -> ImageVectorCreator(indentation = params.get()) }
}

fun KoinComponent.injectImageVectorCreator(indentation: String) = inject<ImageVectorCreator> {
    parametersOf(indentation)
}

fun KoinComponent.injectOutputStrategy(
    path: String?,
    name: String,
    indentation: String
) = inject<OutputStrategy> { parametersOf(path, name, indentation) }

fun KoinComponent.injectVectorSetParser(fileExtension: String) = inject<VectorSetParser> {
    parametersOf(fileExtension)
}
