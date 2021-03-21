package nikolay.egorov.cli.commands.internal

import nikolay.egorov.cli.commands.AbstractCommand
import nikolay.egorov.cli.commands.ExecutionStatus
import nikolay.egorov.cli.runtime.Environment
import java.io.File
import java.io.InputStream
import java.io.OutputStream

/**
 * Class for the ls command execution
 */

class Ls(name: String, args: List<String>) : AbstractCommand(name, args) {

    override fun execute(inp: InputStream, out: OutputStream, err: OutputStream): ExecutionStatus {
        val args = getArgs()
        val currentDirectory: File = Environment.instance.currentDirectory.toFile()
        if (args.isEmpty()) {
            writeToOut(currentDirectory.list(), out)
            return ExecutionStatus.PROCEED
        }

        if (args.size > 1) {
            err.write("Too many arguments".toByteArray())
            err.write(System.lineSeparator().toByteArray())
            return ExecutionStatus.PROCEED
        }

        val name = args[0]
        val file = File(currentDirectory, name)
        if (!file.exists()) {
            err.write("No such file or directory".toByteArray())
            err.write(System.lineSeparator().toByteArray())
        } else if (file.isDirectory) {
            writeToOut(file.list(), out)
        } else if (file.isFile) {
            out.write(name.toByteArray())
            out.write(System.lineSeparator().toByteArray())
        }
        return ExecutionStatus.PROCEED
    }

    private fun writeToOut(path: Array<String>?, out: OutputStream) {
        if (path != null) {
            for (p in path) {
                out.write(p.toByteArray())
                out.write(System.lineSeparator().toByteArray())
            }
        }
    }
}