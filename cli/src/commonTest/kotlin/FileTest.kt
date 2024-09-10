import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.writeString
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class FileTest {

    private val path = "./Hello.kt"
    private val ioPath = Path(path)
    private val fileSystem = SystemFileSystem

    @AfterTest
    fun deleteFile() {
        if (fileSystem.exists(ioPath)) {
            fileSystem.delete(ioPath)
        }
    }

    @Test
    fun `extension extracted from name`() {
        assertEquals(
            expected = "svg",
            actual = File(name = "test.svg", content = "Hello").extension
        )
    }

    @Test
    fun `reading not existent path throws error`() {
        assertFailsWith<IllegalArgumentException> {
            File.read(fileSystem = fileSystem, path = "./hello")
        }
    }

    @Test
    fun `reading existent path returns file`() {
        val sink = fileSystem.sink(ioPath).buffered()
        sink.writeString(string = "Hello SVG")
        sink.close()

        assertEquals(
            expected = File(name = "Hello.kt", content = "Hello SVG"),
            actual = File.read(fileSystem = fileSystem, path = path)
        )
    }

    @Test
    fun `writing file creates file in system`() {
        val file = File(name = "Hello.kt", content = """fun main() { println("Hello, World!") }""")
        file.write(fileSystem = fileSystem, path = path)

        assertTrue(fileSystem.exists(path = ioPath))
    }
}
