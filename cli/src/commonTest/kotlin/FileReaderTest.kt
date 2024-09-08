import file.File
import file.FileReader
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class FileReaderTest {

    @Test
    fun not_existent_path_throws_error() {
        val reader = FileReader(FakeFileSystem())
        assertFailsWith<IllegalArgumentException> { reader.read(path = "Hello") }
    }

    @Test
    fun existent_path_returns_file() {
        val system = FakeFileSystem()
        val path = "test.svg"
        system.write(path.toPath(), mustCreate = true) { writeUtf8("Hello SVG") }

        assertEquals(
            expected = File(name = "test.svg", content = "Hello SVG"),
            actual = FileReader(system).read(path)
        )
    }
}
