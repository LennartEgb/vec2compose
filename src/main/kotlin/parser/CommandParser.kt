package parser

class CommandParser {

    private val delimiter = Regex("\\s?[, ]\\s?")
    
    fun parse(value: String): Command {
        val command = value.first()
        require(command in Command.chars) {
            "First character must be a command identifier but was $command. Command was: $value"
        }
        return when (command.uppercase()) {
            "Z" -> Command.Close(isAbsolute = command.isUpperCase())
            "M" -> createMove(value.drop(1), isAbsolute = command.isUpperCase())
            "L" -> createLineTo(value.drop(1), isAbsolute = command.isUpperCase())
            "C" -> createCurveTo(value.drop(1), isAbsolute = command.isUpperCase())
            "H" -> Command.HorizontalLineTo(value.drop(1).toFloat(), isAbsolute = command.isUpperCase())
            "V" -> Command.VerticalLineTo(value.drop(1).toFloat(), isAbsolute = command.isUpperCase())
            else -> error("No command found for $value")
        }
    }

    private fun createMove(eventString: String, isAbsolute: Boolean): Command {
        val (x, y) = eventString.prepare()
        return Command.MoveTo(x = x, y = y, isAbsolute = isAbsolute)
    }

    private fun createLineTo(eventString: String, isAbsolute: Boolean): Command {
        val (x, y) = eventString.prepare()
        return Command.LineTo(x = x, y = y, isAbsolute = isAbsolute)
    }

    private fun createCurveTo(eventString: String, isAbsolute: Boolean): Command {
        val e = eventString.prepare()
        return Command.CurveTo(e[0], e[1], e[2], e[3], e[4], e[5], isAbsolute = isAbsolute)
    }

    private fun String.prepare(): List<Float> = split(delimiter).map(String::toFloat)
}
