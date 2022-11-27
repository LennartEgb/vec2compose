package output

import org.junit.jupiter.api.Test
import kotlin.test.assertIs

internal class OutputStrategyFactoryTest {

    private val factory = OutputStrategyFactory(NameFormatter())
    @Test
    fun `create without output path returns PrintOutputStrategy`() {

        assertIs<PrintOutputStrategy>(factory.create(null, name = "George"))
    }

    @Test
    fun `create with output path returns FileOutputStrategy`() {
        assertIs<FileOutputStrategy>(factory.create("some_file.xml", name = "George"))
    }
}