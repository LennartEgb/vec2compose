package commands

internal class CommandParser {

    private val valueRegex = "[+-]?\\d*[.]?\\d+".toRegex()

    fun parse(value: String): List<Command> {
        val command = value.first()
        require(command in Command.validCommands) {
            "First character must be a command identifier but was $command. Command was: $value"
        }
        val isAbsolute = command.isUpperCase()
        val eventString = value.drop(1)
        return when (command.uppercase()) {
            "Z" -> listOf(Command.Close(isAbsolute = isAbsolute))
            "M" -> createMoves(eventString, isAbsolute = isAbsolute)
            "L" -> createLinesTo(eventString, isAbsolute = isAbsolute)
            "C" -> createCurvesTo(eventString, isAbsolute = isAbsolute)
            "S" -> createReflectiveCurvesTo(eventString, isAbsolute = isAbsolute)
            "H" -> createHorizontalLinesTo(eventString, isAbsolute = isAbsolute)
            "V" -> createVerticalLinesTo(eventString, isAbsolute = isAbsolute)
            else -> error("No command found for $value")
        }
    }

    private fun createHorizontalLinesTo(eventString: String, isAbsolute: Boolean): List<Command> {
        return eventString.prepare()
            .validate(1, "Horizontal line")
            .map { Command.HorizontalLineTo(it, isAbsolute) }
    }

    private fun createVerticalLinesTo(eventString: String, isAbsolute: Boolean): List<Command> {
        return eventString.prepare()
            .validate(1, "Vertical line")
            .map { Command.VerticalLineTo(it, isAbsolute) }
    }

    private fun createReflectiveCurvesTo(eventString: String, isAbsolute: Boolean): List<Command> {
        return eventString.prepare()
            .validate(4, "Reflective curve")
            .windowed(size = 4, step = 4, partialWindows = false)
            .map { Command.ReflectiveCurveTo(x1 = it[0], y1 = it[1], x2 = it[2], y2 = it[3], isAbsolute = isAbsolute) }
    }

    private fun createMoves(eventString: String, isAbsolute: Boolean): List<Command> {
        return eventString.prepare()
            .validate(2, "Move")
            .windowed(size = 2, step = 2, partialWindows = false)
            .map { Command.MoveTo(x = it[0], y = it[1], isAbsolute = isAbsolute) }
    }

    private fun createLinesTo(eventString: String, isAbsolute: Boolean): List<Command> {
        return eventString.prepare()
            .validate(2, "Line")
            .windowed(size = 2, step = 2, partialWindows = false)
            .map { Command.LineTo(x = it[0], y = it[1], isAbsolute = isAbsolute) }
    }

    private fun createCurvesTo(eventString: String, isAbsolute: Boolean): List<Command> {
        return eventString.prepare()
            .validate(6, "Curve")
            .windowed(size = 6, step = 6, partialWindows = false)
            .map { Command.CurveTo(it[0], it[1], it[2], it[3], it[4], it[5], isAbsolute = isAbsolute) }
    }

    private fun String.prepare(): List<Float> = valueRegex.findAll(this).map { it.value }.map(String::toFloat).toList()
    private fun <T> List<T>.validate(count: Int, name: String): List<T> = apply {
        check(value = size % count == 0) { "$name needs $count parameters but was $this" }
    }
}
