package output

import org.junit.jupiter.api.Test
import kotlin.test.assertIs

internal class OutputStrategyFactoryTest {

    @Test
    fun `create without output path returns PrintOutputStrategy`() {
        assertIs<PrintOutputStrategy>(OutputStrategyFactory.create(null))
    }

    @Test
    fun `create with output path returns FileOutputStrategy`() {
        assertIs<FileOutputStrategy>(OutputStrategyFactory.create("some_file.xml"))
    }
}