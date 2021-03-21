package nikolay.egorov.cli.commands

import nikolay.egorov.cli.analysis.exceptions.InvalidCommandCreationException
import nikolay.egorov.cli.commands.internal.AbstractCommandTestBase
import nikolay.egorov.cli.runtime.Environment
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CommandCreatorTest : AbstractCommandTestBase() {

    @Test
    fun creationTest() {
        var created = CommandCreator.instance.createCommand("x", listOf("1"), true)
        created.execute(emptyStream, outStream, errorStream)
        assertEquals("1", Environment.instance.getVariableValue("x"))

//        created = CommandCreator.instance.createCommand("ls", emptyList(), false)
//        assertTrue(created is ExternalCommand)
    }

    @Test(expected = InvalidCommandCreationException::class)
    fun badCreationTest() {
        CommandCreator.instance.createCommand("x", emptyList(), true)
    }
}
