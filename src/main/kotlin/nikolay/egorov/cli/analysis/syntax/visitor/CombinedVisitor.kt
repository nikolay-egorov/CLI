package nikolay.egorov.cli.analysis.syntax.visitor

import nikolay.egorov.cli.analysis.syntax.representation.AssignStatement
import nikolay.egorov.cli.analysis.syntax.representation.CommandStatement
import nikolay.egorov.cli.commands.AbstractCommand

/**
 * All-in class which performs two stages: pre and post visit for
 * each statement by calling corresponding classes
 *
 * @see SubstitutionVisitor
 * @see PostVisitor
 * @see Visitor
 */
class CombinedVisitor : Visitor() {

    private val substitutionVisitor: SubstitutionVisitor = SubstitutionVisitor()
    private val postVisitor: PostVisitor = PostVisitor()

    override fun visit(statement: AssignStatement) {
        statement.accept(substitutionVisitor)
        statement.accept(postVisitor)
    }

    override fun visit(statement: CommandStatement) {
        statement.accept(substitutionVisitor)
        statement.accept(postVisitor)
    }

    fun getExecutableResults(): ArrayList<AbstractCommand> {
        return postVisitor.executableTasks
    }
}
