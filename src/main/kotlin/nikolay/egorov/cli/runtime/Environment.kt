package nikolay.egorov.cli.runtime

import org.jetbrains.annotations.NotNull
import java.nio.file.Path
import java.nio.file.Paths

class Environment private constructor() : EnvironmentInterface {
    private val variables: MutableMap<String, String> = HashMap()
    private var myCurrentDirectory = Paths.get(System.getProperty("user.dir"))

    override fun getVariableValue(variable: String): String {
        return if (variables.containsKey(variable)) {
            variables[variable]!!
        } else System.getenv().getOrDefault(variable, "")
    }

    override fun putVariableValue(variable: String, value: String) {
        variables[variable] = value
    }

    @get:NotNull
    override var currentDirectory: Path
        get() = myCurrentDirectory
        set(value) {myCurrentDirectory = value}

    companion object {
        private val INSTANCE = Environment()
        /**
         * Returns the [Environment] instance
         *
         * @return The [Environment]
         */
        val instance: EnvironmentInterface
            get() = INSTANCE
    }
}
