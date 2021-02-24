package nikolay.egorov.cli.commands.supported

import nikolay.egorov.cli.commands.AbstractCommand
import nikolay.egorov.cli.commands.ExecutionStatus
import nikolay.egorov.cli.runtime.Environment
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.nio.file.Files
import java.nio.file.Path

/**
 * Class for the cat command execution
 */
class Cat(name: String, args: List<String>) : AbstractCommand(name, args) {
    override fun execute(inp: InputStream, out: OutputStream, err: OutputStream): ExecutionStatus {
        val args = getArgs()
        if (args.isEmpty()) {
            inp.copyTo(out)
            return ExecutionStatus.PROCEED
        }

        for (filename in getArgs()) {
            val currentDirectory: Path = Environment.instance.currentDirectory
            val file: File = currentDirectory.resolve(filename).toFile()
            if (!file.exists()) {
                err.write(String.format("File %s does not exist", filename).toByteArray())
                err.write(System.lineSeparator().toByteArray())
            }
            Files.copy(file.toPath(), out)
        }

        return ExecutionStatus.PROCEED
    }

}