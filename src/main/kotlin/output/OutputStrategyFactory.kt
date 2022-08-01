package output

internal object OutputStrategyFactory {
    fun create(outputPath: String?): OutputStrategy {
        return outputPath?.let { FileOutputStrategy(it) }
            ?: PrintOutputStrategy()
    }
}