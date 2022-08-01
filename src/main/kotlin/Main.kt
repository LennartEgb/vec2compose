import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.required
import output.FileOutputStrategy
import output.PrintOutputStrategy
import java.io.File

fun main(args: Array<String>) {
    /// Arguments
    val argParser = ArgParser(programName = "vec2compose")
    val input by argParser.option(
        type = ArgType.String,
        shortName = "i",
        fullName = "input",
        description = "Input file"
    ).required()
    val output by argParser.option(
        type = ArgType.String,
        shortName = "o",
        fullName = "output",
        description = "Output file"
    )
    argParser.parse(args)

    /// Output strategies
    val outputValue = output
    val out = if (outputValue != null) FileOutputStrategy(pathname = outputValue) else PrintOutputStrategy()

    /// load file content
    val file = File(input).takeIf { it.exists() } ?: error("File $input does not exist")
    out.write(Injection.XMLFileParser.parse(file))
}