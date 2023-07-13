import imagevector.ImageVectorParser
import okio.FileSystem
import okio.Path.Companion.toPath
import output.NameFormatter
import output.OutputStrategyFactory

fun main(args: Array<String>) {
    val arguments = Arguments(args)
    val path = arguments.input.toPath()

    val file = FileReader(fileSystem = FileSystem.SYSTEM).read(path)

    val nameFormatter = NameFormatter()
    val outputStrategy = OutputStrategyFactory(nameFormatter = nameFormatter)
        .create(arguments.output, file.title)

    FileParser(vectorSetParser = VectorSetParserFactory.create(file.extension), imageVectorParser = ImageVectorParser())
        .parse(content = file.content, name = file.title)
        .onSuccess { outputStrategy.write(it) }
        .onFailure { println("Error occurred: ${it.message}") }
}

private val File.title: String get() = name.removeSuffix(".$extension")
