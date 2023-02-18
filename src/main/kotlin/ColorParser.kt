internal interface ColorParser {
    fun parse(color: String): VectorSet.Path.FillColor?
}

