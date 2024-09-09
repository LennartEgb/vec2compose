import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.required

internal class Arguments(args: Array<String>) {
    private val argParser = ArgParser(programName = "vec2compose")

    val input: String by argParser.option(
        type = ArgType.String,
        fullName = "input",
        shortName = "i",
        description = "Input file"
    ).required()

    val output: String? by argParser.option(
        type = ArgType.String,
        shortName = "o",
        fullName = "output",
        description = "Output file"
    )

    val packageName: String? by argParser.option(
        type = ArgType.String,
        shortName = "p",
        fullName = "package",
        description = "Projects package name"
    )

    init {
        argParser.parse(args)
    }
}
