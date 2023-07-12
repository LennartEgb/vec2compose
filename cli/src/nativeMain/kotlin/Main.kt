import imagevector.ImageVectorParser
import okio.FileSystem
import okio.Path.Companion.toPath
import output.NameFormatter
import output.OutputStrategyFactory

fun main(args: Array<String>) {
    val arguments = Arguments(args)
    val system = FileSystem.SYSTEM
    val path = arguments.input.toPath()
    if (!system.exists(path)) error("File ${arguments.input} does not exist")

    val name = path.name
    val extension = name.takeLastWhile { it != '.' }
    val content = system.read(path) { readUtf8() }
    val title = name.removeSuffix(extension).removeSuffix(".")

    val nameFormatter = NameFormatter()
    val outputStrategy = OutputStrategyFactory(nameFormatter = nameFormatter).create(arguments.output, title)

    FileParser(vectorSetParser = VectorSetParserFactory.create(extension), imageVectorParser = ImageVectorParser())
        .parse(content = content, name = title)
        .onSuccess { outputStrategy.write(it) }
        .onFailure { println("Error occurred: ${it.message}") }
}
