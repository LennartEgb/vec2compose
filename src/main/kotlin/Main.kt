import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.required
import output.FileOutputStrategy
import output.PrintOutputStrategy
import java.io.File

fun main(args: Array<String>) {
    val argParser = ArgParser(programName = "vec2compose")
    val input: String by argParser.option(
        type = ArgType.String,
        shortName = "i",
        fullName = "input",
        description = "Input file"
    ).required()
    val output: String? by argParser.option(
        type = ArgType.String,
        shortName = "o",
        fullName = "output",
        description = "Output file"
    )
    argParser.parse(args)

    val outputValue = output
    val out = if (outputValue != null) FileOutputStrategy(pathname = outputValue) else PrintOutputStrategy()

    // load file content
    val file = File(input)
    out.write(Injection.XMLParser.parse(file))
}