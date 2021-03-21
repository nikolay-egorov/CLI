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
        assertEquals("183 \t781 \t5763$lineSeparator", getOutputString())
        clear()
    }

    @Test
    fun wcPipeTest() {
        clear()
//        var result = processLine("echo 123 | wc ")
//        assertEquals(ExecutionStatus.PROCEED, result)
//        assertEquals("1 \t1 \t4$lineSeparator", getOutputString())

        clear()
        var result = processLine("echo '' | wc")
        assertEquals(ExecutionStatus.PROCEED, result)
        assertEquals("1 \t0 \t1$lineSeparator", getOutputString())

//        clear()
//        result = processLine("echo 312 asd | wc")
//        assertEquals(ExecutionStatus.PROCEED, result)
//        assertEquals("1 \t2 \t8$lineSeparator", getOutputString())
    }
}
