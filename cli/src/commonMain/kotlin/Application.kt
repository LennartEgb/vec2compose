import di.injectImageVectorCreator
import di.injectOutputStrategy
import di.injectVectorSetParser
import file.FileReader
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import output.NameFormatter

internal class Application(
    private val indentation: String = "    "
) : KoinComponent {

    private val fileReader by inject<FileReader>()
    private val imageVectorCreator by injectImageVectorCreator(indentation = indentation)

    fun run(arguments: Arguments) {
        val file = fileReader.read(arguments.input)
        val name = NameFormatter.format(file.name)
        val outputStrategy by injectOutputStrategy(
            path = arguments.output,
            name = name,
            indentation = indentation
        )
        val vectorSetParser by injectVectorSetParser(fileExtension = file.extension)

        vectorSetParser.parse(file.content)
            .mapCatching { imageVectorCreator.create(name = name, vectorSet = it) }
            .onSuccess(outputStrategy::write)
            .onFailure { println("Error occurred: ${it.message}") }
    }
}
