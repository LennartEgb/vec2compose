import imagevector.ImageVectorParser
import okio.FileSystem
import output.NameFormatter
import output.OutputStrategyFactory

fun main(args: Array<String>) {
    val arguments = Arguments(args)

    val fileSystem = FileSystem.SYSTEM
    val file = FileReader(fileSystem = fileSystem).read(arguments.input)

    val outputStrategy = OutputStrategyFactory(fileSystem = fileSystem)
        .create(arguments.output, NameFormatter.format(file.name))

    FileParser(vectorSetParser = VectorSetParserFactory.create(file.extension), imageVectorParser = ImageVectorParser())
        .parse(content = file.content, name = file.name)
        .onSuccess { outputStrategy.write(it) }
        .onFailure { println("Error occurred: ${it.message}") }
}
