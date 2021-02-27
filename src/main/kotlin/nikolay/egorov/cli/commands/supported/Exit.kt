package nikolay.egorov.cli.commands.supported

import nikolay.egorov.cli.commands.AbstractCommand
import nikolay.egorov.cli.commands.ExecutionStatus
import java.io.InputStream
import java.io.OutputStream

/**
 * Class for the exit command execution
 */
class Exit(name: String, args: List<String>) : AbstractCommand(name, args) {
    override fun execute(inp: InputStream, out: OutputStream, err: OutputStream): ExecutionStatus {
        return ExecutionStatus.INTERRUPT
    }
}
