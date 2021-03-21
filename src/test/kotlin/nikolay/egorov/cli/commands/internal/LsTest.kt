package nikolay.egorov.cli.commands.internal

import nikolay.egorov.cli.commands.ExecutionStatus
import org.junit.Test

import org.junit.Assert.*
import java.nio.file.Paths

class LsTest: AbstractCommandTestBase() {

    private val lineSeparator = System.lineSeparator()

    @Test
    fun executeFile() {
        val result = processLine("ls gradlew")
        assertEquals(ExecutionStatus.PROCEED, result)
        assertEquals("gradlew$lineSeparator", getOutputString())
    }

    @Test
    fun executeDirectory() {
        val result = processLine("ls src")
        assertEquals(ExecutionStatus.PROCEED, result)
        assertEquals("main${lineSeparator}test$lineSeparator", getOutputString())
    }

    @Test
    fun executeLsWithoutArg() {
        val result = processLine("ls")
        assertEquals(ExecutionStatus.PROCEED, result)
        val path = Paths.get(System.getProperty("user.dir")).toFile()
        val list = path.list()
        var ans = ""
        if (list != null) {
            for (l in list) {
                ans += l.toString() + lineSeparator
            }
        }
        assertEquals(ans.toByteArray().toString(Charsets.UTF_8), getOutputString())
    }
}