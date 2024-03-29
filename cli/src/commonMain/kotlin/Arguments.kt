import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.DefaultRequiredType
import kotlinx.cli.SingleNullableOption
import kotlinx.cli.SingleOption
import kotlinx.cli.required

internal class Arguments(args: Array<String>) {
    private val inputOption: SingleOption<String, DefaultRequiredType.Required>
    private val outputOption: SingleNullableOption<String>

    init {
        val argParser = ArgParser(programName = "vec2compose")
        inputOption = argParser.option(
            type = ArgType.String,
            shortName = "i",
            fullName = "input",
            description = "Input file"
        ).required()
        outputOption = argParser.option(
            type = ArgType.String,
            shortName = "o",
            fullName = "output",
            description = "Output file"
        )
        argParser.parse(args)
    }

    val input: String by inputOption
    val output: String? by outputOption
}
