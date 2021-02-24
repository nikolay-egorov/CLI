package nikolay.egorov.cli.analysis.syntax.representation

import nikolay.egorov.cli.analysis.syntax.visitor.PostVisitor
import nikolay.egorov.cli.analysis.syntax.visitor.PreVisitor
import nikolay.egorov.cli.analysis.syntax.visitor.Visitor
import java.util.*

/**
 * Intermediate commands sequence representation
 * Basically, just a list of commands
 *
 * @see PreVisitor
 * @see PostVisitor
 */
class CommandChain(commandList: ArrayList<Statement>) : Statement {
    var commandsList: ArrayList<Statement> = commandList

    override fun accept(visitor: Visitor) {
        visitor.visit(this)
    }
}