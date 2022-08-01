import output.OutputStrategyFactory
import parser.FileParserFactory
import java.io.File

fun main(args: Array<String>) {
    val arguments = Arguments(args)
    val outputStrategy = OutputStrategyFactory.create(arguments.output)
    val file = File(arguments.input).takeIf { it.exists() } ?: error("File ${arguments.input} does not exist")
    FileParserFactory.createFileParser(file)
        .parse(file)
        .onSuccess { outputStrategy.write(it) }
        .onFailure { println("Error occurred: ${it.message}") }
}