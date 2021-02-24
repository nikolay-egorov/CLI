package nikolay.egorov.cli.commands

import nikolay.egorov.cli.analysis.exceptions.InvalidExecutionException
import java.io.InputStream
import java.io.OutputStream

enum class ExecutionStatus {
    PROCEED,
    INTERRUPT
}

/**
 * Common interface of what any executable object should do
 */
interface ExecutableInterface {
    /**
     * Executes command providing all streams
     *
     * @param inp The input
     * @param out The output for execution output
     * @param err The output for errors
     *
     * @throws InvalidExecutionException if an error during the invocation occurred

     * @return status on continuing execution
     * @see ExecutionStatus
     */
    @Throws(InvalidExecutionException::class)
    fun execute(inp: InputStream, out: OutputStream, err: OutputStream): ExecutionStatus
}