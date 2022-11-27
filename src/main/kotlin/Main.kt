import fileparser.FileParserFactory
import output.NameFormatter
import output.OutputStrategyFactory
import java.io.File

fun main(args: Array<String>) {
    val arguments = Arguments(args)
    val file = File(arguments.input).takeIf { it.exists() } ?: error("File ${arguments.input} does not exist")
    val nameFormatter = NameFormatter()
    val outputStrategy = OutputStrategyFactory(nameFormatter = nameFormatter).create(arguments.output, file.name)
    FileParserFactory.createFileParser(file)
        .parse(file)
        .onSuccess { outputStrategy.write(it) }
        .onFailure { println("Error occurred: ${it.message}") }
}