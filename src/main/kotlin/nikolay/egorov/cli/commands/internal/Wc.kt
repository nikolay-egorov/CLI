package nikolay.egorov.cli.commands.internal

import nikolay.egorov.cli.commands.AbstractCommand
import nikolay.egorov.cli.commands.ExecutionStatus
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.OutputStream

/**
 * Class for the wc command execution
 */
class Wc(name: String, args: List<String>) : AbstractCommand(name, args) {
    override fun execute(inp: InputStream, out: OutputStream, err: OutputStream): ExecutionStatus {
        val reader = inp.bufferedReader()

        val allText = if (getArgs().isEmpty()) {
            reader.use(BufferedReader::readText)
        } else {
            File(getArgs().first()).readText()
        }
        val linesCount = if (allText.isBlank()) 1 else allText.split("\n").size - 1

        val wordsCount = allText.split("\\s+".toRegex()).filterNot { it in "\r\n\t" }.size
        val bytesCount = if (allText.isBlank()) 1 else allText.chars().count() - 1

        out.write("$linesCount \t$wordsCount \t$bytesCount".toByteArray())
        out.write(System.lineSeparator().toByteArray())
        return ExecutionStatus.PROCEED
    }
}
