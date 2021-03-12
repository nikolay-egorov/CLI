package nikolay.egorov.cli.commands

import nikolay.egorov.cli.analysis.exceptions.InvalidExecutionException
import nikolay.egorov.cli.commands.internal.AbstractCommandTestBase
import org.junit.Assert.assertEquals
import org.junit.Test

class ExecutionTest : AbstractCommandTestBase() {

    @Test
    fun executeValid() {
        val status = processLine("x=12")
        assertEquals(ExecutionStatus.PROCEED, status)
    }

    @Test(expected = InvalidExecutionException::class)
    fun executeUnknown() {
        val status = processLine("x")
        assertEquals(ExecutionStatus.INTERRUPT, status)
    }
}
