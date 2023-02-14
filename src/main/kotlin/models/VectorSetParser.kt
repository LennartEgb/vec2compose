package models

internal interface VectorSetParser {
    fun parse(content: String): Result<VectorSet>
}