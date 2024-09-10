import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class FileTest {

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
            File.read(fileSystem = FakeFileSystem(), path = "Hello")
        }
    }

    @Test
    fun `reading existent path returns file`() {
        val system = FakeFileSystem()
        val path = "test.svg"
        system.write(path.toPath(), mustCreate = true) { writeUtf8("Hello SVG") }

        assertEquals(
            expected = File(name = "test.svg", content = "Hello SVG"),
            actual = File.read(fileSystem = system, path = path)
        )
    }

    @Test
    fun `writing file creates file in system`() {
        val system = FakeFileSystem()
        val path = "./tmp/Hello.kt"
        val file = File(name = "Hello.kt", content = """fun main() { println("Hello, World!") }""")
        file.write(fileSystem = system, path = path)

        assertTrue(system.exists(path = path.toPath()))
    }
}
