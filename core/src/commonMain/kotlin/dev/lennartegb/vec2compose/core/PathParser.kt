package dev.lennartegb.vec2compose.core

import dev.lennartegb.vec2compose.core.commands.Command

typealias Path = List<Command>

private val regex = "[A-z][^A-z]*".toRegex()

fun parsePath(pathCode: String): Path = regex
    .findAll(pathCode)
    .flatMap { CommandParser.parse(it.value) }
    .toList()
