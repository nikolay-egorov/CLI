package nikolay.egorov.cli.commands.supported

import nikolay.egorov.cli.commands.ExecutionStatus
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class EchoTest : AbstractCommandTestBase() {

    private val lineSeparator = System.lineSeparator()

    @Test
    fun echoTest() {
        var executionStatus = processLine("echo 123")
        assertEquals(ExecutionStatus.PROCEED, executionStatus)
        assertTrue(outputData.isNotEmpty())
        assertEquals("123$lineSeparator", getOutputString())

        clear()
        executionStatus = processLine("echo")
        assertEquals(ExecutionStatus.PROCEED, executionStatus)
        assertEquals(lineSeparator, getOutputString())

    }
}