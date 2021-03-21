package nikolay.egorov.cli.commands.internal

import nikolay.egorov.cli.commands.AbstractCommand
import nikolay.egorov.cli.commands.ExecutionStatus
import nikolay.egorov.cli.runtime.Environment
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.nio.file.Paths

/**
* Class for the cd command execution
*/

class Cd(name: String, args: List<String>) : AbstractCommand(name, args) {

    override fun execute(inp: InputStream, out: OutputStream, err: OutputStream): ExecutionStatus {
        val args = getArgs()
        if (args.isEmpty()) {
            Environment.instance.currentDirectory = Paths.get(System.getProperty("user.dir"))
            return ExecutionStatus.PROCEED
        }

        if (args.size > 1) {
            err.write("Too many arguments".toByteArray())
            err.write(System.lineSeparator().toByteArray())
            return ExecutionStatus.PROCEED
        }

        val currentDirectory: String = Environment.instance.currentDirectory.toAbsolutePath().toString()
        val name = args[0]
        val file = File(currentDirectory, name)
        when {
            file.isFile -> {
                err.write("Not a directory".toByteArray())
                err.write(System.lineSeparator().toByteArray())
            }
            file.isDirectory -> {
                Environment.instance.currentDirectory = file.toPath()
            }
            else -> {
                err.write("No such file or directory".toByteArray())
                err.write(System.lineSeparator().toByteArray())
            }
        }
        return ExecutionStatus.PROCEED
    }
}