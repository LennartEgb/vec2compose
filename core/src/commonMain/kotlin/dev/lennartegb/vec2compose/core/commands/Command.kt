package dev.lennartegb.vec2compose.core.commands

import kotlin.jvm.JvmInline

@JvmInline
value class Command(internal val value: String) {

    internal companion object {

        private val regex = "[+-]?\\d*[.]?\\d+".toRegex()

        val commandSpecMap = mapOf(
            'a' to ArcTo,
            'c' to CurveTo,
            'h' to HorizontalLineTo,
            'l' to LineTo,
            'm' to MoveTo,
            'q' to QuadraticBezierTo,
            's' to ReflectiveCurveTo,
            't' to ReflectiveQuadraticBezierTo,
            'v' to VerticalLineTo,
            'z' to Close
        )
    }

    init {
        val original = value[0]
        val spec = commandSpecMap[original.lowercaseChar()]
        requireNotNull(spec) { "Command is not in ${commandSpecMap.keys}. Was $original" }
        val argsCount = getArguments().size
        require(argsCount == spec.argsCount || argsCount % spec.argsCount == 0) {
            "Wrong argument count for $original. Expected ${spec.argsCount} but was $argsCount"
        }
    }

    private fun getArguments(): List<String> = regex.findAll(value).map { it.value }.toList()

    override fun toString(): String {
        val original = value[0]
        val spec = commandSpecMap.getValue(original.lowercaseChar())

        if (spec.argsCount == 0) {
            return "${spec.name}()"
        }

        val args = getArguments()
        val relativeSuffix = if (original.isLowerCase()) "Relative" else ""
        return if (args.size == spec.argsCount) {
            "${spec.name}$relativeSuffix(${spec.getArguments(args)})"
        } else {
            val windows = args.windowed(spec.argsCount, spec.argsCount)
            // NOTE: Special case of move command following a lineTo command
            if (spec is MoveTo) {
                windows.mapIndexed { index, arg ->
                    if (index == 0) {
                        "${spec.name}$relativeSuffix(${spec.getArguments(arg)})"
                    } else {
                        "${LineTo.name}$relativeSuffix(${spec.getArguments(arg)})"
                    }
                }.joinToString("\n")
            } else {
                windows.joinToString("\n") {
                    "${spec.name}$relativeSuffix(${spec.getArguments(it)})"
                }
            }
        }
    }
}
