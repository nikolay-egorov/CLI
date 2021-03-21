package nikolay.egorov.cli.commands.internal

import nikolay.egorov.cli.commands.ExecutionStatus
import nikolay.egorov.cli.runtime.Environment
import org.junit.Test

import org.junit.Assert.*
import java.io.File
import java.nio.file.Paths

class CdTest: AbstractCommandTestBase() {

    private val lineSeparator = System.lineSeparator()

    @Test
    fun executeWithDirectory() {
        val result = processLine("cd src")
        assertEquals(ExecutionStatus.PROCEED, result)
        assertEquals(
            Environment.instance.currentDirectory.toString(),
            File(Paths.get(System.getProperty("user.dir")).toFile(), "src").toString()
        )
    }

    @Test
    fun executeWithoutDirectory() {
        val result = processLine("cd src | cd")
        assertEquals(ExecutionStatus.PROCEED, result)
        assertEquals(Environment.instance.currentDirectory.toString(),
            Paths.get(System.getProperty("user.dir")).toString())
    }

    @Test
    fun executeWithDirectory1() {
        val result = processLine("cd src/main")
        assertEquals(ExecutionStatus.PROCEED, result)
        assertEquals(Environment.instance.currentDirectory.toString(),
            File(Paths.get(System.getProperty("user.dir")).toFile(), "src/main").toString())
    }
}