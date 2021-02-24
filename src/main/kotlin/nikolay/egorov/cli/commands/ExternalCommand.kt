package nikolay.egorov.cli.commands

import nikolay.egorov.cli.analysis.exceptions.InvalidExecutionException
import java.io.InputStream
import java.io.OutputStream

/**
 * Class for any other command which is not supported by the interpreter
 */
class ExternalCommand(name: String, args: List<String>) : AbstractCommand(name, args) {

    @Throws(InvalidExecutionException::class)
    override fun execute(inp: InputStream, out: OutputStream, err: OutputStream): ExecutionStatus {
        val command = ArrayList(getArgs())
        command.add(0, getName())
        val pb = ProcessBuilder(command)
        pb.redirectError(ProcessBuilder.Redirect.PIPE)
            .redirectInput(ProcessBuilder.Redirect.PIPE)
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
        try {
            val start = pb.start()
            val inputStream = start.inputStream
            inputStream.copyTo(out)
        } catch (exception: Exception) {
            throw InvalidExecutionException("Invalid invocation attempt of ${getName()} command")
        }

        return ExecutionStatus.PROCEED
    }
}