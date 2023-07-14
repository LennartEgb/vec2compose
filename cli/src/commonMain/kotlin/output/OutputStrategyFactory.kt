package output

import imagevector.ImageVectorImportProvider
import okio.FileSystem

internal class OutputStrategyFactory(
    private val nameFormatter: NameFormatter,
    private val fileSystem: FileSystem,
) {
    private val importProvider = ImageVectorImportProvider()

    fun create(outputPath: String?, name: String): OutputStrategy {
        return outputPath?.let {
            FileOutputStrategy(
                name = nameFormatter.format(name),
                pathname = it,
                importProvider = importProvider,
                fileSystem = fileSystem
            )
        } ?: OutputStrategy(::println)
    }
}
