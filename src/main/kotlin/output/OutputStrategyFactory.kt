package output

import imagevector.ImageVectorImportProvider

internal object OutputStrategyFactory {
    private val importProvider = ImageVectorImportProvider()

    fun create(outputPath: String?): OutputStrategy {
        return outputPath?.let { FileOutputStrategy(it, importProvider) }
            ?: PrintOutputStrategy()
    }
}