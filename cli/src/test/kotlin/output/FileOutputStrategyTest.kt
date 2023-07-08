package output

import imagevector.ImageVectorImportProvider
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertTrue

class FileOutputStrategyTest {

    private val name = ""
    private val pathname = "./tmp.kt"

    @AfterEach
    fun deleteFile() {
        File(pathname).delete()
    }

    @Test
    fun `writes to file`() {
        val strategy = FileOutputStrategy(
            name = name,
            pathname = pathname,
            importProvider = ImageVectorImportProvider()
        )
        strategy.write("CONTENT")

        assertTrue(File(pathname).exists())
    }
}
