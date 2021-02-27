package nikolay.egorov.cli.commands.supported

import nikolay.egorov.cli.commands.ExecutionStatus
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GrepTest : AbstractCommandTestBase() {

    @Test
    fun simpleTest() {
        val status = processLine("echo \"Hello, world, hello\"  | grep World")
        assertTrue(getOutputString()!!.isEmpty())
        assertEquals(ExecutionStatus.PROCEED, status)
    }

    @Test
    fun fileTest() {
        var status = processLine("grep -i -w done gradlew")
        assertTrue(getOutputString()!!.isNotEmpty())
        assertEquals(ExecutionStatus.PROCEED, status)
        clear()

        status = processLine("grep -w Done gradlew")
        assertTrue(getOutputString()!!.isEmpty())
        assertEquals(ExecutionStatus.PROCEED, status)
        clear()
    }

    @Test
    fun regularTest() {
        val status = processLine("grep -i -w d[a-z]+e gradlew")
        assertTrue(getOutputString()!!.isNotEmpty())
        assertEquals(ExecutionStatus.PROCEED, status)
    }
}
