package nikolay.egorov.cli.commands.supported

import nikolay.egorov.cli.analysis.lexical.Lexer
import nikolay.egorov.cli.analysis.syntax.parser.Parser
import nikolay.egorov.cli.runtime.Environment
import org.junit.Assert.assertEquals
import org.junit.Test


class AssignTest : AbstractCommandTestBase() {

    @Test
    fun simpleVarTest() {
        Assign("a", listOf("123")).execute(emptyStream, outStream, errorStream)
        assertEquals("123", Environment.instance.getVariableValue("a"))
    }

    @Test
    fun changingVarTest() {
        Environment.instance.putVariableValue("a", "13")
        Assign("a", listOf("12")).execute(emptyStream, outStream, errorStream)
        assertEquals("12", Environment.instance.getVariableValue("a"))
    }

    @Test
    fun testIfAssign() {
        var lexer = Lexer("x=123")
        assertEquals(true, Parser(lexer.lexInput()).isNextAssignForTest())

        lexer = Lexer("x = ")
        assertEquals(false, Parser(lexer.lexInput()).isNextAssignForTest())
    }


}