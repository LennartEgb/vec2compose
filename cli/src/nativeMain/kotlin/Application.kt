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
        val outputStrategy by injectOutputStrategy(
            path = arguments.output,
            name = NameFormatter.format(file.name),
            indentation = indentation
        )
        val vectorSetParser by injectVectorSetParser(fileExtension = file.extension)

        vectorSetParser.parse(file.content)
            .mapCatching { imageVectorCreator.create(name = file.name, vectorSet = it) }
            .onSuccess(outputStrategy::write)
            .onFailure { println("Error occurred: ${it.message}") }
    }
}
