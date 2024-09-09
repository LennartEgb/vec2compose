import dev.lennartegb.vec2compose.core.KotlinFileContentCreator
import dev.lennartegb.vec2compose.core.VectorSet
import dev.lennartegb.vec2compose.core.imagevector.ImageVectorCreator
import dev.lennartegb.vec2compose.svg.svgVectorSetParser
import dev.lennartegb.vec2compose.vectorDrawable.vectorSetParser
import kotlinx.io.files.FileSystem
import kotlinx.io.files.SystemFileSystem
import output.NameFormatter
import output.Output

fun interface VectorSetConverter : (VectorSet) -> String

fun String.toOutput(fileSystem: FileSystem) = Output {
    File.write(fileSystem = fileSystem, path = this, content = it)
}

internal class Application(
    private val fileSystem: FileSystem = SystemFileSystem,
    private val imageVectorCreator: ImageVectorCreator,
    private val kotlinFileContentCreator: KotlinFileContentCreator
) {
    fun run(arguments: Arguments) {
        val file = File.read(fileSystem = fileSystem, path = arguments.input)
        val name = NameFormatter.format(file.name)

        val writeFile = arguments.output != null

        val output = arguments.output?.toOutput(fileSystem)
            ?: Output(::println)

        val convert = VectorSetConverter { set ->
            if (writeFile) {
                kotlinFileContentCreator.create(
                    packageName = arguments.packageName,
                    name = name,
                    vectorSet = set
                )
            } else {
                imageVectorCreator.create(name = name, vectorSet = set)
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
