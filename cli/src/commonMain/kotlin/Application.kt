import dev.lennartegb.vec2compose.core.ImageVector
import dev.lennartegb.vec2compose.core.KotlinFileContentCreator
import dev.lennartegb.vec2compose.core.imagevector.ImageVectorCreator
import dev.lennartegb.vec2compose.svg.svgImageVectorParser
import dev.lennartegb.vec2compose.vectorDrawable.xmlImageVectorParser
import kotlinx.io.files.FileSystem
import kotlinx.io.files.SystemFileSystem

private fun interface ImageVectorConverter : (ImageVector) -> String
private fun interface Output : (String) -> Unit

private fun String.toOutput(fileSystem: FileSystem) = Output {
    File.write(fileSystem = fileSystem, path = this, content = it)
}

internal class Application(
    private val fileSystem: FileSystem = SystemFileSystem,
    private val imageVectorCreator: ImageVectorCreator,
    private val kotlinFileContentCreator: KotlinFileContentCreator
) {
    fun run(arguments: Arguments) {
        val file = File.read(fileSystem = fileSystem, path = arguments.input)
        val name = file.nameWithoutExtension

        val writeFile = arguments.output != null

        val output = arguments.output?.toOutput(fileSystem)
            ?: Output(::println)

        val convert = ImageVectorConverter { set ->
            if (writeFile) {
                kotlinFileContentCreator.create(
                    packageName = arguments.packageName,
                    name = name,
                    imageVector = set
                )
            } else {
                imageVectorCreator.create(name = name, imageVector = set)
            }
        }

        val imageVectorParser = when (file.extension) {
            "svg" -> svgImageVectorParser()
            "xml" -> xmlImageVectorParser()
            else -> throw IllegalArgumentException("${file.extension} files are not supported")
        }

        imageVectorParser.parse(file.content)
            .mapCatching { convert(it) }
            .onSuccess { output(it) }
            .onFailure { println("Error occurred: ${it.message}") }
    }
}
