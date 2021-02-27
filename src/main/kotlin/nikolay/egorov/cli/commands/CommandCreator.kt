package nikolay.egorov.cli.commands

import nikolay.egorov.cli.analysis.exceptions.InvalidCommandCreationException
import nikolay.egorov.cli.commands.supported.Assign
import nikolay.egorov.cli.commands.supported.Cat
import nikolay.egorov.cli.commands.supported.Echo
import nikolay.egorov.cli.commands.supported.Exit
import nikolay.egorov.cli.commands.supported.Pwd
import nikolay.egorov.cli.commands.supported.Wc

/**
 * Helper class for creating any commands by passing arguments
 * Holds static instance
 */
class CommandCreator {
    /**
     * Default command for external commands
     */
    private val defaultCommand: Class<ExternalCommand> = ExternalCommand::class.java

    companion object {
        val instance = CommandCreator()

        /**
         * Map of supported commands implemented in code
         */
        private val runnableCommands: MutableMap<String, Class<out AbstractCommand>> = HashMap()

        init {
            runnableCommands["echo"] = Echo::class.java
            runnableCommands["cat"] = Cat::class.java
            runnableCommands["pwd"] = Pwd::class.java
            runnableCommands["wc"] = Wc::class.java
            runnableCommands["exit"] = Exit::class.java
        }
    }

    /**
     * Creates command by name and list of arguments
     *
     * @param name Command name
     * @param args Command arguments as list of strings
     * @param isAssignExpr The bool indicator telling if it's an assignment
     * @return Created command
     */
    fun createCommand(name: String, args: List<String>, isAssignExpr: Boolean = false): AbstractCommand {
        if (runnableCommands.containsKey(name)) {
            return runnableCommands[name]!!.getConstructor(String::class.java, MutableList::class.java)
                .newInstance(name, args)
        }
        return if (isAssignExpr) {
            if (args.isEmpty()) {
                throw InvalidCommandCreationException("Right-side shall not be empty")
            }
            Assign::class.java.getConstructor(String::class.java, MutableList::class.java).newInstance(name, args)
        } else
            defaultCommand.getConstructor(String::class.java, MutableList::class.java).newInstance(name, args)
    }
}
