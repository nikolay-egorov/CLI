package nikolay.egorov.cli.commands.supported

import nikolay.egorov.cli.commands.AbstractCommand
import nikolay.egorov.cli.commands.ExecutionStatus
import nikolay.egorov.cli.runtime.Environment
import java.io.InputStream
import java.io.OutputStream

/**
 * Class for an assignment command execution
 */
class Assign(name: String, args: List<String>) : AbstractCommand(name, args) {
    override fun execute(inp: InputStream, out: OutputStream, err: OutputStream): ExecutionStatus {
        val expression = getName()
        Environment.instance.putVariableValue(expression, getArgs().joinToString())
        return ExecutionStatus.PROCEED
    }

}