package nikolay.egorov.cli.commands.supported

import nikolay.egorov.cli.commands.ExecutionStatus
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.PrintStream
import java.nio.file.Files

class GrepTest : AbstractCommandTestBase() {

    private val separator: String = System.lineSeparator()

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

    @Test
    fun matchFullFile() {
        val tempFile = Files.createTempFile("test", "regex")
        Files.newOutputStream(tempFile).use { os ->
            val printer = PrintStream(os)
            printer.println("Hello")
            printer.println("reap")
            printer.println("grepper")
            printer.println("reaper")
            printer.println("reaper")
        }
        val regex = "reap"

        val status = processLine("grep -w $regex $tempFile")
        assertEquals(ExecutionStatus.PROCEED, status)
        assertEquals(regex + separator, getOutputString())
        clear()
    }

    @Test
    fun testCaseSensitiveFile() {
        val tempFile = Files.createTempFile("test", "regex")
        Files.newOutputStream(tempFile).use { os ->
            val printer = PrintStream(os)
            printer.println("Hello")
            printer.println("heap")
            printer.println("Grep")
            printer.println("grepper")
            printer.println("reaper")
            printer.println("Reaper")
            printer.println("birds fly high")
        }
        var regex = "reap?+"
        var status = processLine("grep $regex $tempFile")
        assertEquals(ExecutionStatus.PROCEED, status)
        assertEquals("reaper$separator", getOutputString())
        clear()

        regex = "Reap?+"
        status = processLine("grep $regex $tempFile")
        assertEquals(ExecutionStatus.PROCEED, status)
        assertEquals("Reaper$separator", getOutputString())
        clear()
    }

    @Test
    fun printAfterFile() {
        clear()
        val tempFile = Files.createTempFile("test", "regex")
        Files.newOutputStream(tempFile).use { os ->
            val printer = PrintStream(os)
            printer.println("Hello")
            printer.println("reap")
            printer.println("grep")
            printer.println("grepper")
            printer.println("reaper")
            printer.println("birds fly high")
        }
        val regex = "reap?+"

        val status = processLine("grep -A 1 $regex $tempFile")
        assertEquals(ExecutionStatus.PROCEED, status)
        assertEquals(
            "reap" + separator + "grep" +
                separator + "reaper" + separator + "birds fly high" + separator,
            getOutputString()
        )
        clear()
    }
}
