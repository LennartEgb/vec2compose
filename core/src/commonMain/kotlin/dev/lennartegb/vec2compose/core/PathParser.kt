package dev.lennartegb.vec2compose.core

import dev.lennartegb.vec2compose.core.commands.Command

private val regex = "[A-z][^A-z]*".toRegex()

typealias Path = List<Command>

val Path.raw: String get() = joinToString("") { it.value }

@Suppress("FunctionName")
fun Path(pathCode: String): Path = regex.findAll(pathCode).map { Command(it.value) }.toList()
