object VectorSetParserFactory {
    fun create(fileExtension: String): VectorSetParser {
        val validExtensions = mapOf(
            "xml" to Injection.VectorDrawableParser,
            "svg" to Injection.SVGParser
        )
        val extension = fileExtension.lowercase()
        require(extension in validExtensions.keys) { "No parser found for file extension: $fileExtension" }
        return validExtensions.getValue(extension)
    }
}
