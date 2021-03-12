package nikolay.egorov.cli.commands.internal

import nikolay.egorov.cli.commands.AbstractCommand
import nikolay.egorov.cli.commands.ExecutionStatus
import java.io.InputStream
import java.io.OutputStream

/**
 * Class for the echo command execution
 */
class Echo(name: String, args: List<String>) : AbstractCommand(name, args) {
    override fun execute(inp: InputStream, out: OutputStream, err: OutputStream): ExecutionStatus {
        val output = getArgs().joinToString(separator = " ")
        out.write(output.toByteArray())
        out.write(System.lineSeparator().toByteArray())
        return ExecutionStatus.PROCEED
    }
}
