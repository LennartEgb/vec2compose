package dev.lennartegb.vec2compose.core

import dev.lennartegb.vec2compose.core.commands.ArcTo
import dev.lennartegb.vec2compose.core.commands.Close
import dev.lennartegb.vec2compose.core.commands.Command
import dev.lennartegb.vec2compose.core.commands.CurveTo
import dev.lennartegb.vec2compose.core.commands.HorizontalLineTo
import dev.lennartegb.vec2compose.core.commands.LineTo
import dev.lennartegb.vec2compose.core.commands.MoveTo
import dev.lennartegb.vec2compose.core.commands.QuadraticBezierTo
import dev.lennartegb.vec2compose.core.commands.ReflectiveCurveTo
import dev.lennartegb.vec2compose.core.commands.ReflectiveQuadraticBezierTo
import dev.lennartegb.vec2compose.core.commands.VerticalLineTo

private val regex = "[+-]?\\d*[.]?\\d+".toRegex()
private val validCommands: CharArray = charArrayOf(
    'A',
    'a',
    'C',
    'c',
    'H',
    'h',
    'L',
    'l',
    'M',
    'm',
    'Q',
    'q',
    'S',
    's',
    'T',
    't',
    'V',
    'v',
    'Z',
    'z'
)

internal fun parseCommand(value: String): List<Command> {
    val command = value.first()
    require(command in validCommands) {
        "First character must be a command identifier but was $command. Command was: $value"
    }
    val isAbsolute = command.isUpperCase()
    val eventString = value.drop(1)
    return when (command.uppercase()) {
        "A" -> createArcTo(eventString, isAbsolute = isAbsolute)
        "C" -> createCurvesTo(eventString, isAbsolute = isAbsolute)
        "H" -> createHorizontalLinesTo(eventString, isAbsolute = isAbsolute)
        "L" -> createLinesTo(eventString, isAbsolute = isAbsolute)
        "M" -> createMoves(eventString, isAbsolute = isAbsolute)
        "Q" -> createQuadraticBezier(eventString, isAbsolute = isAbsolute)
        "S" -> createReflectiveCurvesTo(eventString, isAbsolute = isAbsolute)
        "T" -> createReflectiveQuadraticBezier(eventString, isAbsolute = isAbsolute)
        "V" -> createVerticalLinesTo(eventString, isAbsolute = isAbsolute)
        "Z" -> listOf(Close)
        else -> error("No command found for $value")
    }
}

private fun createArcTo(eventString: String, isAbsolute: Boolean): List<Command> {
    return eventString.prepare()
        .validate(count = 7, name = "ArcTo")
        .windowed(size = 7, step = 7, partialWindows = false)
        .map {
            ArcTo(
                horizontalEllipseRadius = it[0],
                verticalEllipseRadius = it[1],
                theta = it[2],
                isMoreThanHalf = it[3] == 1f,
                isPositiveArc = it[4] == 1f,
                x1 = it[5],
                y1 = it[6],
                isAbsolute = isAbsolute
            )
        }
}

private fun createReflectiveQuadraticBezier(
    eventString: String,
    isAbsolute: Boolean
): List<ReflectiveQuadraticBezierTo> {
    return eventString.prepare()
        .validate(count = 2, name = "Reflective quadratic bezier")
        .windowed(size = 2, step = 2, partialWindows = false)
        .map { ReflectiveQuadraticBezierTo(x = it[0], it[1], isAbsolute = isAbsolute) }
}

private fun createQuadraticBezier(eventString: String, isAbsolute: Boolean): List<Command> {
    return eventString.prepare()
        .validate(count = 4, "Quadratic bezier")
        .windowed(size = 4, step = 4, partialWindows = false)
        .map {
            QuadraticBezierTo(
                x1 = it[0],
                y1 = it[1],
                x2 = it[2],
                y2 = it[3],
                isAbsolute = isAbsolute
            )
        }
}

private fun createHorizontalLinesTo(eventString: String, isAbsolute: Boolean): List<Command> {
    return eventString.prepare()
        .validate(1, "Horizontal line")
        .map { HorizontalLineTo(it, isAbsolute) }
}

private fun createVerticalLinesTo(eventString: String, isAbsolute: Boolean): List<Command> {
    return eventString.prepare()
        .validate(1, "Vertical line")
        .map { VerticalLineTo(it, isAbsolute) }
}

private fun createReflectiveCurvesTo(eventString: String, isAbsolute: Boolean): List<Command> {
    return eventString.prepare()
        .validate(4, "Reflective curve")
        .windowed(size = 4, step = 4, partialWindows = false)
        .map {
            ReflectiveCurveTo(
                x1 = it[0],
                y1 = it[1],
                x2 = it[2],
                y2 = it[3],
                isAbsolute = isAbsolute
            )
        }
}

private fun createMoves(eventString: String, isAbsolute: Boolean): List<Command> {
    val commands = eventString.prepare()
        .validate(2, "Move")
        .windowed(size = 2, step = 2, partialWindows = false)
    val moveTo = commands.first().let {
        MoveTo(x = it[0], y = it[1], isAbsolute = isAbsolute)
    }
    val lineTo = commands.drop(1).map {
        LineTo(
            x = it[0],
            y = it[1],
            isAbsolute = isAbsolute
        )
    }
    return listOf(moveTo) + lineTo
}

private fun createLinesTo(eventString: String, isAbsolute: Boolean): List<Command> {
    return eventString.prepare()
        .validate(2, "Line")
        .windowed(size = 2, step = 2, partialWindows = false)
        .map { LineTo(x = it[0], y = it[1], isAbsolute = isAbsolute) }
}

private fun createCurvesTo(eventString: String, isAbsolute: Boolean): List<Command> {
    return eventString.prepare()
        .validate(6, "Curve")
        .windowed(size = 6, step = 6, partialWindows = false)
        .map {
            CurveTo(
                it[0],
                it[1],
                it[2],
                it[3],
                it[4],
                it[5],
                isAbsolute = isAbsolute
            )
        }
}

private fun String.prepare(): List<Float> =
    regex.findAll(this).map { it.value }.map(String::toFloat).toList()

private fun <T> List<T>.validate(count: Int, name: String): List<T> = apply {
    check(value = size % count == 0) { "$name needs $count parameters but was $this" }
}