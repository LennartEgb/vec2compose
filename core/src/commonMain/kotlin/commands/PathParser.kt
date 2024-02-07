package commands

class PathParser(private val commandParser: CommandParser) {
    private val regex = "[A-z][^A-z]*".toRegex()
    fun parse(pathCode: String): List<Command> =
        regex.findAll(pathCode).flatMap { commandParser.parse(it.value) }.toList()
}
