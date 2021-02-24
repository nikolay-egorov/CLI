package nikolay.egorov.cli.analysis.exceptions

import nikolay.egorov.cli.commands.AbstractCommand
import nikolay.egorov.cli.commands.CommandCreator

/**
 * Exception represents invalid callable command object creation.
 *
 * @see AbstractCommand
 * @see CommandCreator
 */
class InvalidCommandCreationException(message: String) : RuntimeException(message)