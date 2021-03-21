package nikolay.egorov.cli.runtime

import java.nio.file.Path

/**
 * Environment's use contract description
 */
interface EnvironmentInterface {
    /**
     * Returns the value of variable object
     * @param variable The name of a variable
     *
     * @return The value of variable or empty by default
     */
    fun getVariableValue(variable: String): String

    /**
     * Adds new variable to execution environment
     * @param variable The name of variable
     * @param value    The value of variable
     */
    fun putVariableValue(variable: String, value: String)

    /**
     * Returns current working directory
     * @return The absolute path to directory
     */
    val currentDirectory: Path
}
