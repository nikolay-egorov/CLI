package nikolay.egorov.cli.analysis.syntax.parser

import nikolay.egorov.cli.analysis.exceptions.ParserException
import nikolay.egorov.cli.analysis.lexical.Lexem
import nikolay.egorov.cli.analysis.lexical.LexemType
import nikolay.egorov.cli.analysis.syntax.representation.Statement

interface ParserInterface {

    /**
     * Performs parsing
     * @return generalised CommandChain
     */
    @Throws(ParserException::class)
    fun parse(): Statement

    /**
     * Tries to match underling lexem for a specific type
     * @return boolean value as a result of predicate invocation
     */
    fun match(type: LexemType): Boolean

    /**
     * Gets next token
     * @return next Lexem
     */
    fun advance(): Lexem

    /**
     * Look up for a lexem at a specific position
     * @return lexem at a position
     */
    fun getAt(position: Int): Lexem
}
