package parser

internal class PathParser(private val commandParser: CommandParser) {
    fun parse(pathCode: String): List<Command> {
        if (pathCode.isEmpty()) return emptyList()

        val command = pathCode.first()

        val commandValues = pathCode.drop(1).let {
            it.substring(range = 0 until (it.indexOfFirst { c -> c in Command.chars }.takeIf { it != -1 } ?: it.length))
        }
        val tail = pathCode.drop(1).substring(commandValues.length)
        return listOf(commandParser.parse(command + commandValues)) + parse(tail)
    }
}

