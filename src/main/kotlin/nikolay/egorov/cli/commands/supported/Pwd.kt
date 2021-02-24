package nikolay.egorov.cli.commands.supported

import nikolay.egorov.cli.commands.AbstractCommand
import nikolay.egorov.cli.commands.ExecutionStatus
import nikolay.egorov.cli.runtime.Environment
import java.io.InputStream
import java.io.OutputStream


/**
 * Class for the pwd command execution
 */
class Pwd(name: String, args: List<String>) : AbstractCommand(name, args) {
    override fun execute(inp: InputStream, out: OutputStream, err: OutputStream): ExecutionStatus {
        out.write(Environment.instance.currentDirectory.toAbsolutePath().toString().toByteArray())
        out.write(System.lineSeparator().toByteArray())
        return ExecutionStatus.PROCEED
    }


}