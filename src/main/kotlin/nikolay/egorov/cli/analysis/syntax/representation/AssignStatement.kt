package nikolay.egorov.cli.analysis.syntax.representation

import nikolay.egorov.cli.analysis.lexical.Lexem
import nikolay.egorov.cli.analysis.syntax.visitor.PostVisitor
import nikolay.egorov.cli.analysis.syntax.visitor.SubstitutionVisitor
import nikolay.egorov.cli.analysis.syntax.visitor.Visitor

/**
 * Since assignment is meant to creates variables, it's similar to
 * @see CommandStatement
 * but provides other semantics
 *
 * @see SubstitutionVisitor
 * @see PostVisitor
 */
class AssignStatement(val leftSide: Lexem, val rightSide: ArrayList<Lexem>) : Statement {

    override fun accept(visitor: Visitor) {
        visitor.visit(this)
    }

    override fun toString(): String {
        return "$leftSide = $rightSide)"
    }
}
