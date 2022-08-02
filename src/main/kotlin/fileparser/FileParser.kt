package fileparser

import java.io.File

internal interface FileParser {
    fun parse(file: File): Result<String>
}