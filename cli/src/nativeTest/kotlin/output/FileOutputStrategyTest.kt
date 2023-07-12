package output

import imagevector.ImageVectorImportProvider
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem
import kotlin.test.Test
import kotlin.test.assertTrue

class FileOutputStrategyTest {

    private val name = ""
    private val pathname = "./tmp.kt"

    @Test
    fun `writes to file`() {
        val system = FakeFileSystem()
        val strategy = FileOutputStrategy(
            fileSystem = system,
            name = name,
            pathname = pathname,
            importProvider = ImageVectorImportProvider()
        )
        strategy.write("CONTENT")

        assertTrue(system.exists(pathname.toPath()))
    }
}
