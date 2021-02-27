package nikolay.egorov.cli.analysis.exceptions

import nikolay.egorov.cli.commands.AbstractCommand
import nikolay.egorov.cli.commands.ExecutableInterface

/**
 * Exception represents invalid any command invocation attempt
 *
 * @see AbstractCommand
 * @see ExecutableInterface
 */
class InvalidExecutionException(message: String) : Exception(message)
