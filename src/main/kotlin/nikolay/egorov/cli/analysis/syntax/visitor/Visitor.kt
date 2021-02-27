package nikolay.egorov.cli.analysis.syntax.visitor

import nikolay.egorov.cli.analysis.syntax.representation.AssignStatement
import nikolay.egorov.cli.analysis.syntax.representation.CommandChain
import nikolay.egorov.cli.analysis.syntax.representation.CommandStatement
import nikolay.egorov.cli.analysis.syntax.representation.Statement
import nikolay.egorov.cli.commands.CommandCreator

/**
 * Base abstract class for traversing intermediate representation
 * Splits process in 2 phased: pre-visit tries to actualise all variables values;
 * post-visit builds commands based on intermediate representation
 *
 * @see PreVisitor
 * @see PostVisitor
 * @see CommandCreator
 * @see Statement
 */
abstract class Visitor {
    abstract fun visit(statement: AssignStatement)

    abstract fun visit(statement: CommandStatement)

    fun visit(statement: CommandChain) {
        statement.commandsList.forEach { st -> st.accept(this) }
    }
}
