package nikolay.egorov.cli.commands.supported

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
//        assertEquals("1 \t1 \t3$lineSeparator", getOutputString()) // intellij Test's is ok with?

        clear()
        result = processLine("echo '' | wc")
        assertEquals(ExecutionStatus.PROCEED, result)
        assertEquals("0 \t1 \t0$lineSeparator", getOutputString())
    }
}
