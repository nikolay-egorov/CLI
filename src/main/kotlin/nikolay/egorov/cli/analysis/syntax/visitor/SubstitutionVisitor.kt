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

    private val delimitersSet = setOf<Char>('.', ',', '_', '-', ':', ';')

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
     * For any word tries to see it in regex.
     * If it's inside, substitution via EnvironmentInterface attempt shall be called
     *
     * @see EnvironmentInterface
     */
    private fun actualizeLexem(lexem: Lexem) {
        val type = lexem.type
        if (type == LexemType.WORD || type == LexemType.DOUBLE_QUOTED_W || type == LexemType.QUOTED_W) {

            convertToWord(lexem)
            val res = StringBuffer()
            val matcher = varPattern.matcher(lexem.text)
            val varName = lexem.text

            while (matcher.find()) {
                var foundSecond = false
                var trailingVarName: String
                if (matcher.group(2) != null) {
                    trailingVarName = matcher.group(2)
                    foundSecond = true
                } else {
                    trailingVarName = matcher.group(3)
                }

                if (foundSecond) {
                    val shift = res.length + trailingVarName.length
                    val atShift = if (shift + 1 < varName.length) varName[shift] else varName[shift - 1]
                    res.append(trailingVarName)
                    if (delimitersSet.contains(atShift)) {
                        res.append(atShift)
                    }
                    continue
                }
                res.append(environment.getVariableValue(trailingVarName))
            }

            if (res.isNotEmpty() && lexem.text != res.toString()) {
                lexem.text = res.toString()
            }

            environment.putVariableValue(varName, lexem.text)
        }
    }

    private fun convertToWord(doubleQuoted: Lexem) {
        if (doubleQuoted.type == LexemType.DOUBLE_QUOTED_W) {
            doubleQuoted.type = LexemType.WORD
            doubleQuoted.text.replaceFirst('\"', ' ')
            doubleQuoted.text.dropLast(1)
        } else if (doubleQuoted.type == LexemType.QUOTED_W) {
            doubleQuoted.type = LexemType.WORD
            doubleQuoted.text.replaceFirst('\'', ' ')
            doubleQuoted.text.dropLast(1)
        }
    }
}
