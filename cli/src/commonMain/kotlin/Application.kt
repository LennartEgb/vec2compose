import dev.lennartegb.vec2compose.core.KotlinFileContentCreator
import dev.lennartegb.vec2compose.core.VectorSet
import dev.lennartegb.vec2compose.core.imagevector.ImageVectorCreator
import dev.lennartegb.vec2compose.svg.svgVectorSetParser
import dev.lennartegb.vec2compose.vectorDrawable.vectorSetParser
import okio.FileSystem
import output.NameFormatter
import output.Output

fun interface VectorSetConverter : (VectorSet) -> String

internal class Application(
    private val fileSystem: FileSystem = FileSystem.SYSTEM,
    private val imageVectorCreator: ImageVectorCreator,
    private val kotlinFileContentCreator: KotlinFileContentCreator
) {
    fun run(arguments: Arguments) {
        val file = File.read(fileSystem = fileSystem, path = arguments.input)
        val name = NameFormatter.format(file.name)

        val writeFile = arguments.output != null

        val output = arguments.output
            ?.let { path ->
                Output { File.write(fileSystem = fileSystem, path = path, content = it) }
            }
            ?: Output(::println)

        val convert = VectorSetConverter {
            if (writeFile) {
                kotlinFileContentCreator.create(packageName = null, name = name, vectorSet = it)
            } else {
                imageVectorCreator.create(name = name, vectorSet = it)
            }
        }

        val vectorSetParser = when (file.extension) {
            "svg" -> svgVectorSetParser()
            "xml" -> vectorSetParser()
            else -> throw IllegalArgumentException("${file.extension} files are not supported")
        }

        vectorSetParser.parse(file.content)
            .mapCatching { convert(it) }
            .onSuccess { output(it) }
            .onFailure { println("Error occurred: ${it.message}") }
    }
}
