package nikolay.egorov.cli.analysis.syntax.representation

import nikolay.egorov.cli.analysis.syntax.visitor.Visitor

/**
 * Every element of AST-like structure is bined by this contract
 */
interface Statement {
    /**
     * Allows any visitor to traverse element. Similar to Visitor pattern
     *
     * @see Visitor
     *
     * @param visitor inherited from Visitor class object
     */
    fun accept(visitor: Visitor)
}