import dev.lennartegb.vec2compose.core.FileParser
import dev.lennartegb.vec2compose.core.VectorSetParserFactory
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

    FileParser(
        vectorSetParser = VectorSetParserFactory.create(file.extension),
        imageVectorParser = ImageVectorParser(indentation = indentation),
    ).parse(content = file.content, name = file.name)
        .onSuccess { outputStrategy.write(it) }
        .onFailure { println("Error occurred: ${it.message}") }
}
