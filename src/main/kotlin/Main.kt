import imagevector.ImageVectorParser
import java.io.File
import output.NameFormatter
import output.OutputStrategyFactory

fun main(args: Array<String>) {
    val arguments = Arguments(args)
    val file =
        File(arguments.input).takeIf { it.exists() }
            ?: error("File ${arguments.input} does not exist")
    val vectorSetParserFactory = VectorSetParserFactory(file)
    val nameFormatter = NameFormatter()
    val outputStrategy =
        OutputStrategyFactory(nameFormatter = nameFormatter).create(arguments.output, file.name)

    FileParser(
            vectorSetParser = vectorSetParserFactory.create(),
            imageVectorParser = ImageVectorParser()
        )
        .parse(file)
        .onSuccess { outputStrategy.write(it) }
        .onFailure { println("Error occurred: ${it.message}") }
}
