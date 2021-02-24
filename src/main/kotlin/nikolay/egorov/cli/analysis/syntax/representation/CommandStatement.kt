package nikolay.egorov.cli.analysis.syntax.representation

import nikolay.egorov.cli.analysis.lexical.Lexem
import nikolay.egorov.cli.analysis.syntax.visitor.Visitor

/**
 * Any executable command representation.
 *
 * @param command: it's name
 * @param args is a list of arguments to run with. May be empty
 */
class CommandStatement(val command: Lexem, val args: ArrayList<Lexem>) : Statement {

    /**
     * @see Visitor
     */
    override fun accept(visitor: Visitor) {
        visitor.visit(this)
    }

    override fun toString(): String {
        return "$command ${args.forEach { _ -> toString() }})"
    }


}