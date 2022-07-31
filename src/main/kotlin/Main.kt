import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.required
import output.FileOutput
import output.PrintOutput
import java.io.File

fun main(args: Array<String>) {
    val argParser = ArgParser(programName = "vec2compose")
    val input: String by argParser.option(ArgType.String, shortName = "i", description = "Input file").required()
    val output: String? by argParser.option(ArgType.String, shortName = "o", description = "Output file")
    argParser.parse(args)

    val outputValue = output
    val out = if (outputValue != null) FileOutput(pathname = outputValue) else PrintOutput()

    // load file content
    val file = File(input)
    out.write(Injection.XMLParser.parse(file))
}