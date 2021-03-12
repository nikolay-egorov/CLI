package nikolay.egorov.cli.analysis.syntax.representation

import nikolay.egorov.cli.analysis.syntax.visitor.PostVisitor
import nikolay.egorov.cli.analysis.syntax.visitor.SubstitutionVisitor
import nikolay.egorov.cli.analysis.syntax.visitor.Visitor

/**
 * Intermediate commands sequence representation
 * Basically, just a list of commands
 *
 * @see SubstitutionVisitor
 * @see PostVisitor
 */
class CommandChain(commandList: ArrayList<Statement>) : Statement {
    var commandsList: ArrayList<Statement> = commandList

    override fun accept(visitor: Visitor) {
        visitor.visit(this)
    }
}
