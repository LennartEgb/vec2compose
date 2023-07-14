import imagevector.ImageVectorParser
import okio.FileSystem
import okio.Path.Companion.toPath
import output.NameFormatter
import output.OutputStrategyFactory

fun main(args: Array<String>) {
    val arguments = Arguments(
        args.takeIf { it.isNotEmpty() } ?: arrayOf("-i core/src/commonTest/resources/svg/account_circle_24.svg")
    )
    val path = arguments.input.toPath()

    val fileSystem = FileSystem.SYSTEM
    val file = FileReader(fileSystem = fileSystem).read(path)

    val nameFormatter = NameFormatter()
    val outputStrategy = OutputStrategyFactory(nameFormatter = nameFormatter, fileSystem = fileSystem)
        .create(arguments.output, file.title)

    FileParser(vectorSetParser = VectorSetParserFactory.create(file.extension), imageVectorParser = ImageVectorParser())
        .parse(content = file.content, name = file.name)
        .onSuccess { outputStrategy.write(it) }
        .onFailure { println("Error occurred: ${it.message}") }
}

private val File.title: String get() = name.removeSuffix(".$extension")
