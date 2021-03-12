package nikolay.egorov.cli.commands.internal

import nikolay.egorov.cli.commands.ExecutionStatus
import org.junit.Assert.assertEquals
import org.junit.Test

class ExitTest : AbstractCommandTestBase() {

    @Test
    fun singularTest() {
        val execute = Exit("exit", emptyList()).execute(emptyStream, outStream, errorStream)
        assertEquals(ExecutionStatus.INTERRUPT, execute)
    }

    @Test
    fun exitArgsTest() {
        val execute = Exit("exit", listOf("args1", "112")).execute(emptyStream, outStream, errorStream)
        assertEquals(ExecutionStatus.INTERRUPT, execute)
    }

    @Test
    fun chainedExitTest() {
        val executionStatus = processLine("echo 123 | exit")
        assertEquals(ExecutionStatus.INTERRUPT, executionStatus)
    }
}
