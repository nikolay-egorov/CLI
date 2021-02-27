package nikolay.egorov.cli.commands.supported

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import nikolay.egorov.cli.analysis.exceptions.ParserException
import nikolay.egorov.cli.commands.AbstractCommand
import nikolay.egorov.cli.commands.ExecutionStatus
import java.io.*
import java.util.regex.Pattern


class Grep(name: String, args: List<String>) : AbstractCommand(name, args) {

    class GrepArgs(parser: ArgParser) {
        val ignoreCase by parser.flagging(
            "-i",
            help = "ignore case mode"
        ).default(false)

        val searchFullMatch by parser.flagging(
            "-w", "--word",
            help = "searches for full word match"
        ).default(false)

        val linesAfter by parser.storing(
            "-A",
            help = "print n lines after"
        ) { toInt() }.default(0)

        val regex by parser.positional(
            "REGEX",
            help = "regular expression to search for"
        ).addValidator {
            if (value.isEmpty()) {
                throw ParserException("Missing regex pattern", null)
            }
        }

        val file by parser.positional(
            "FILE",
            help = "file to search in"
        ).default(null)

    }


    override fun execute(inp: InputStream, out: OutputStream, err: OutputStream): ExecutionStatus {
        val lineSeparator = System.lineSeparator()
        val parseInto: GrepArgs?
        try {
            parseInto = ArgParser(getArgs().toTypedArray()).parseInto(::GrepArgs)
        } catch (ex: ParserException) {
            err.write(ex.toString().toByteArray())
            err.write(lineSeparator.toByteArray())
            return ExecutionStatus.PROCEED
        }
        var stringToSearch = parseInto.regex
        if (parseInto.searchFullMatch) {
            stringToSearch = "(?<!\\p{L})$stringToSearch(?!\\p{L})"
        }
        val pattern = Pattern.compile(stringToSearch, if (parseInto.ignoreCase) Pattern.CASE_INSENSITIVE else 0)
        var shouldPrintAfter = parseInto.linesAfter


        var finalInput = inp
        if (parseInto.file != null) {
            val file = File(parseInto.file!!)
            if (!(file.isFile && file.canRead())) {
                err.write("File ${parseInto.file} is invalid or cannot be read".toByteArray())
                err.write(lineSeparator.toByteArray())
                return ExecutionStatus.PROCEED
            }

            finalInput = file.inputStream()
        }


        val reader = BufferedReader(InputStreamReader(finalInput))
        val writer = PrintStream(out)


        for (line in reader.lines()) {
            if (pattern.matcher(line).find()) {
                shouldPrintAfter = 0
                writer.println(line)
            } else if (shouldPrintAfter <= parseInto.linesAfter - 1) {
                shouldPrintAfter++
                writer.println(line)
            }
        }
        writer.close()
        reader.close()

        return ExecutionStatus.PROCEED
    }
}