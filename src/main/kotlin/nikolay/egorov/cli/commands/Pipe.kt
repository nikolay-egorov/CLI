package nikolay.egorov.cli.commands

import nikolay.egorov.cli.analysis.exceptions.InvalidExecutionException
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

/**
 * Class for managing sequential execution
 * Has left-side command
 * Has right-side list of commands (may be empty)
 */
class Pipe(private val leftSide: ExecutableInterface, private val rightSide: List<ExecutableInterface>) :
    ExecutableInterface {
    /**
     * Dynamic storage for all input redirection.
     * Stores output of a command in buffer,
     * so that the next command in line gets it as an input
     */
    class PipeUtility {
        var offset = 0
        val buffer = ArrayList<Int>(2048)
        val inputStream = object : InputStream() {
            @Throws(IOException::class)
            override fun read(): Int {
                return if (offset < buffer.size) buffer[offset++] else -1
            }
        }
        val outputStream = object : OutputStream() {
            @Throws(IOException::class)
            override fun write(b: Int) {
                buffer.add(b)
            }
        }

        fun closeOutput() {
            outputStream.close()
        }
    }

    @Throws(InvalidExecutionException::class)
    override fun execute(inp: InputStream, out: OutputStream, err: OutputStream): ExecutionStatus {
        val pipeUtility = PipeUtility()
        val outputFinal = if (rightSide.isEmpty()) out else pipeUtility.outputStream
        var result = leftSide.execute(inp, outputFinal, err)
        pipeUtility.closeOutput()
        if (result == ExecutionStatus.INTERRUPT) {
            return ExecutionStatus.INTERRUPT
        }
        if (rightSide.isEmpty()) {
            return result
        }

        val first = rightSide.first()
        val other = rightSide.slice(1 until rightSide.size)

        result = Pipe(first, other).execute(pipeUtility.inputStream, out, err)
        return result
    }
}
