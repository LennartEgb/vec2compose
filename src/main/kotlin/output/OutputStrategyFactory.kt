package output

import imagevector.ImageVectorImportProvider

internal class OutputStrategyFactory(
    private val nameFormatter: NameFormatter
) {
    private val importProvider = ImageVectorImportProvider()

    fun create(outputPath: String?, name: String): OutputStrategy {
        return outputPath?.let { FileOutputStrategy(nameFormatter.format(name), it, importProvider) }
            ?: PrintOutputStrategy()
    }
}
