package nikolay.egorov.cli

import nikolay.egorov.cli.analysis.lexical.Lexer
import nikolay.egorov.cli.analysis.syntax.parser.Parser
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
        val lex = Lexer(line)
        val parser = Parser(lex.lexInput())
        val parsedResults = parser.parse()
        val combinedVisitor = CombinedVisitor()
        parsedResults.accept(combinedVisitor)
//        println(parsedResults.toString())

        val tasks = combinedVisitor.getExecutableResults()
        var pipe = Pipe(tasks.first(), emptyList())
        if (tasks.size > 1) {
            pipe = Pipe(tasks.first(), tasks.slice(1 until tasks.size))
        }

        return pipe.execute(System.`in`, System.out, System.err)
    }
}