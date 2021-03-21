package nikolay.egorov.cli.commands.internal

import nikolay.egorov.cli.commands.ExecutionStatus
import org.junit.Assert
import org.junit.Test

class PwdTest : AbstractCommandTestBase() {

    @Test
    fun pwdTest() {
        var result = processLine("pwd")
        Assert.assertEquals(ExecutionStatus.PROCEED, result)
        Assert.assertTrue(getOutputString()!!.isNotEmpty())

        result = processLine("pwd 123")
        Assert.assertEquals(ExecutionStatus.PROCEED, result)
        Assert.assertTrue(getOutputString()!!.isNotEmpty())
    }
}
