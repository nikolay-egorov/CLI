package nikolay.egorov.cli.commands.internal

import nikolay.egorov.cli.commands.ExecutionStatus
import org.junit.Assert.assertEquals
import org.junit.Test

class WcTest : AbstractCommandTestBase() {

    private val lineSeparator = System.lineSeparator()

    @Test
    fun plainWcTest() {
        val result = processLine("wc gradlew")
        assertEquals(ExecutionStatus.PROCEED, result)
        assertEquals("183 \t1181 \t5581$lineSeparator", getOutputString())
    }

    @Test
    fun wcPipeTest() {
        var result = processLine("echo 123 | wc ")
        assertEquals(ExecutionStatus.PROCEED, result)
        assertEquals("1 \t1 \t4$lineSeparator", getOutputString())

        clear()
        result = processLine("echo '' | wc")
        assertEquals(ExecutionStatus.PROCEED, result)
        assertEquals("1 \t0 \t1$lineSeparator", getOutputString())

        clear()
        result = processLine("echo \"123312 asd \" | wc")
        assertEquals(ExecutionStatus.PROCEED, result)
        assertEquals("1 \t2 \t12$lineSeparator", getOutputString())
    }
}
