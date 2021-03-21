package nikolay.egorov.cli

import nikolay.egorov.cli.analysis.exceptions.InvalidCommandCreationException
import nikolay.egorov.cli.analysis.exceptions.InvalidExecutionException
import nikolay.egorov.cli.analysis.exceptions.LexException
import nikolay.egorov.cli.analysis.exceptions.ParserException
import nikolay.egorov.cli.analysis.lexical.Lexer
import nikolay.egorov.cli.analysis.syntax.parser.Parser
import nikolay.egorov.cli.analysis.syntax.representation.Statement
import nikolay.egorov.cli.analysis.syntax.visitor.CombinedVisitor
import nikolay.egorov.cli.commands.ExecutionStatus
import nikolay.egorov.cli.commands.Pipe
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Class for managing user interactions at a higher level
 */
class ShellLoop {

    fun startSession() {
        val inputReader = BufferedReader(InputStreamReader(System.`in`))
        var line = inputReader.readLine()
        while (line != null) {
            if (processLine(line) == ExecutionStatus.INTERRUPT) {
                println("Quiting shell...")
                return
            }
            line = inputReader.readLine()
        }
    }

    private fun processLine(line: String): ExecutionStatus {
        val err = System.err
        val parsedResults: Statement
        val lineSeparator = System.lineSeparator()

        try {
            val lex = Lexer(line)
            val parser = Parser(lex.lexInput())
            parsedResults = parser.parse()
        } catch (ex: LexException) {
            err.write(ex.toString().toByteArray())
            err.write(lineSeparator.toByteArray())
            return ExecutionStatus.PROCEED
        } catch (ex: ParserException) {
            err.write(ex.toString().toByteArray())
            err.write(lineSeparator.toByteArray())
            return ExecutionStatus.PROCEED
        }

        val combinedVisitor = CombinedVisitor()
        try {
            parsedResults.accept(combinedVisitor)
        } catch (ex: InvalidCommandCreationException) {
            err.write(ex.toString().toByteArray())
            err.write(lineSeparator.toByteArray())
            return ExecutionStatus.PROCEED
        }

        val tasks = combinedVisitor.getExecutableResults()
        if (tasks.isEmpty()) {
            return ExecutionStatus.PROCEED
        }

        var pipe = Pipe(tasks.first(), emptyList())
        if (tasks.size > 1) {
            pipe = Pipe(tasks.first(), tasks.slice(1 until tasks.size))
        }

        val status: ExecutionStatus
        try {
            status = pipe.execute(System.`in`, System.out, err)
        } catch (ex: InvalidExecutionException) {
            err.write(ex.toString().toByteArray())
            err.write(lineSeparator.toByteArray())
            return ExecutionStatus.PROCEED
        }

        return status
    }
}
