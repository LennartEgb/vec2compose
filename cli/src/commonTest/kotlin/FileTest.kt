import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.writeString
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals
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
        assertNotEquals(illegal = "", actual = File.read(fileSystem, path).content)
    }

    @Test
    fun `nameWithoutExtension returns name without file extension`() {
        val file = File(name = "Hello.kt", content = "Hello Vector")
        assertEquals(actual = file.nameWithoutExtension, expected = "Hello")
    }

    @Test
    fun `ic close XML file is parsed to IcClose`() {
        val file = File(name = "ic_close.xml", content = "Hello Vector")
        assertEquals(expected = "IcClose", actual = file.nameWithoutExtension)
    }

    @Test
    fun `account circle SVG file is parsed to AccountCircle`() {
        val file = File(name = "account-circle.svg", content = "Hello Vector")
        assertEquals(expected = "AccountCircle", actual = file.nameWithoutExtension)
    }

    @Test
    fun `where To Vote XML file is parsed to WhereToVote`() {
        val file = File(name = "Where To Vote.xml", content = "Hello Vector")
        assertEquals(expected = "WhereToVote", actual = file.nameWithoutExtension)
    }

    @Test
    fun `where ic search 24 is parsed   to IcSearch24`() {
        val file = File(name = "ic_search_24.xml", content = "Hello Vector")
        assertEquals(expected = "IcSearch24", actual = file.nameWithoutExtension)
    }
}
