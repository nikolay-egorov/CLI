package nikolay.egorov.cli.analysis.lexical

/**
 * Contract for lexical analyser usage
 */
interface LexerInterface {
    /**
     * Lexes inputString
     * @return list of lexems
     */
    fun lexInput(): List<Lexem>

    /**
     * From startWith position to words's separator
     * @param startsWith
     */
    fun lexWord(startsWith: Char)

    /**
     * Similar to lexWord, but considers '' symbols
     * @param startsWith
     */
    fun lexQuotedWord(startsWith: Char)

    /**
     * Similar to lexWord, but considers "" symbols
     * @param startsWith
     */
    fun lexOperation(startsWith: Char)

    /**
     * Retrieves next lexed char from the input
     * @return char representation of a lexem
     */
    fun getNext(): Char

    /**
     * Adds lexed token to a container within
     * @param type of a lexem
     * @param lexem as a string
     * @param positionOfStart in the current input
     */
    fun pushLexem(type: LexemType, lexem: String, positionOfStart: Int = -1)
}