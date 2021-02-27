package nikolay.egorov.cli.commands

abstract class AbstractCommand(private val name: String, private val args: List<String>) : ExecutableInterface {

    /**
     * Returns command's name
     * @return name of the command as string
     */
    protected open fun getName(): String {
        return name
    }

    /**
     * Returns list of command arguments
     * @return arguments as list of strings
     */
    protected open fun getArgs(): List<String> {
        return args
    }
}
