import dev.lennartegb.vec2compose.core.imagevector.ImageVectorParser
import okio.FileSystem
import output.NameFormatter
import output.OutputStrategyFactory

fun main(args: Array<String>) {
    val arguments = Arguments(args)

    val fileSystem = FileSystem.SYSTEM
    val file = FileReader(fileSystem = fileSystem).read(arguments.input)
    val indentation = "    "
    val outputStrategy = OutputStrategyFactory(fileSystem = fileSystem)
        .create(outputPath = arguments.output, name = NameFormatter.format(file.name), indentation = indentation)
    val imageVectorParser = ImageVectorParser(indentation = indentation)

    VectorSetParserFactory.create(file.extension)
        .parse(content = file.content)
        .mapCatching { imageVectorParser.parse(name = file.name, vectorSet = it) }
        .onSuccess { outputStrategy.write(it) }
        .onFailure { println("Error occurred: ${it.message}") }
}
