package nikolay.egorov.cli.analysis.exceptions

import nikolay.egorov.cli.analysis.lexical.Lexem
import nikolay.egorov.cli.commands.AbstractCommand
import nikolay.egorov.cli.commands.CommandCreator
import nikolay.egorov.cli.commands.ExecutableInterface

/**
 * Exception represents invalid callable command object creation.
 *
 * @see AbstractCommand
 * @see CommandCreator
 */
class InvalidCommandCreationException(message: String) : Exception(message)

/**
 * Exception represents invalid any command invocation attempt
 *
 * @see AbstractCommand
 * @see ExecutableInterface
 */
class InvalidExecutionException(message: String) : Exception(message)

/**
 * Exception represents unexpected read of raw input state
 */
class LexException(message: String, position: Int) : Exception("[$position] $message")

/**
 * Exception represents unexpected read of lexed input state
 */
class ParserException(message: String, lexem: Lexem?) : Exception("[$lexem] $message")
