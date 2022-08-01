package parser

internal class PathParser(private val commandParser: CommandParser) {
    fun parse(pathCode: String): List<Command> {
        if (pathCode.isEmpty()) return emptyList()
        val (command, rest) = pathCode.partition()
        val commandValues = rest.indexOfFirstCommand().let { rest.substring(range = 0 until it) }
        val tail = rest.substring(startIndex = commandValues.length)
        return commandParser.parse(command + commandValues) + parse(tail)
    }

    private fun CharSequence.partition() = first() to drop(1)
    private fun CharSequence.indexOfFirstCommand(): Int =
        indexOfFirst { it in Command.chars }.takeIf { it != -1 } ?: length
}

