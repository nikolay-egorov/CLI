package nikolay.egorov.cli.commands.internal

import nikolay.egorov.cli.analysis.lexical.Lexer
import nikolay.egorov.cli.analysis.syntax.parser.Parser
import nikolay.egorov.cli.analysis.syntax.visitor.CombinedVisitor
import nikolay.egorov.cli.commands.ExecutionStatus
import nikolay.egorov.cli.commands.Pipe
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

abstract class AbstractCommandTestBase {
    protected val emptyStream = object : InputStream() {
        @Throws(IOException::class)
        override fun read(): Int {
            return -1
        }
    }

    protected var outputData = ArrayList<Byte>()
    protected val outStream = object : OutputStream() {
        @Throws(IOException::class)
        override fun write(b: Int) {
            outputData.add(b.toByte())
        }
    }

    protected var errorData = ArrayList<Byte>()
    protected val errorStream = object : OutputStream() {
        @Throws(IOException::class)
        override fun write(b: Int) {
            errorData.add(b.toByte())
        }
    }

    protected fun clear() {
        emptyStream.readBytes()
        outStream.flush()
        outputData.clear()
    }

    protected open fun getOutputString(): String? {
        return outputData.toByteArray().toString(Charsets.UTF_8)
    }

    protected fun processLine(line: String): ExecutionStatus {
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

        return pipe.execute(emptyStream, outStream, errorStream)
    }
}
