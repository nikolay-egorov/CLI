package nikolay.egorov.cli.analysis.syntax.visitor

import nikolay.egorov.cli.analysis.lexical.Lexem
import nikolay.egorov.cli.analysis.lexical.LexemType
import nikolay.egorov.cli.analysis.syntax.representation.AssignStatement
import nikolay.egorov.cli.analysis.syntax.representation.CommandStatement
import nikolay.egorov.cli.runtime.Environment
import nikolay.egorov.cli.runtime.EnvironmentInterface

/**
 * Class for actualizing lexem values before execution
 * @see Environment
 * @see Visitor
 */
class SubstitutionVisitor(private val environment: EnvironmentInterface = Environment.instance) : Visitor() {

    private val varPattern = Regex("(([\\w\\d]+)|\\$([\\w\\d]+))").toPattern()

    override fun visit(statement: AssignStatement) {
        actualizeLexem(statement.leftSide)
        statement.rightSide.forEach {
            actualizeLexem(it)
        }
    }

    override fun visit(statement: CommandStatement) {
        actualizeLexem(statement.command)
        statement.args.forEach {
            actualizeLexem(it)
        }
    }

    /**
     * For any word or double-braced word tries to see it in regex.
     * If it's inside, substitution via EnvironmentInterface attempt shall be called
     *
     * @see EnvironmentInterface
     */
    private fun actualizeLexem(lexem: Lexem) {
        val type = lexem.type
        if (type === LexemType.WORD || type === LexemType.DOUBLE_QUOTED_W) {

            lexem.type = LexemType.QUOTED_W
            val res = StringBuffer()
            val matcher = varPattern.matcher(lexem.text)
            var varName = lexem.text

            if (matcher.matches()) {
                varName = if (matcher.group(2) != null) {
                    matcher.group(2)
                } else {
                    matcher.group(3)
                }

                res.append(environment.getVariableValue(varName))
                if (res.isNotEmpty()) {
                    lexem.text = res.toString()
                }
            }

            environment.putVariableValue(varName, lexem.text)
        }
    }
}
