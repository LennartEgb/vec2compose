import dev.lennartegb.vec2compose.core.KotlinFileContentCreator
import dev.lennartegb.vec2compose.core.imagevector.ImageVectorCreator

fun main(args: Array<String>) {
    val indentation = " ".repeat(4)
    val imageVectorCreator = ImageVectorCreator(indentation = indentation)
    val kotlinFileContentCreator = KotlinFileContentCreator(
        indentation = indentation,
        imageVectorCreator = imageVectorCreator
    )
    Application(
        kotlinFileContentCreator = kotlinFileContentCreator,
        imageVectorCreator = imageVectorCreator
    ).run(Arguments(args))
}
