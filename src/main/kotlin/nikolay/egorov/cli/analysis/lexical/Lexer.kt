package nikolay.egorov.cli.analysis.lexical

import nikolay.egorov.cli.analysis.exceptions.LexException

/**
 * Base class for lexical analysis phase
 * @see LexerInterface
 */
class Lexer(givenString: String) : LexerInterface {

    private val fedString: String = givenString
    private val tokens: ArrayList<Lexem> = ArrayList()
    private var currPos = 0
    private val stopLexems = setOf('=', '|', '\u0000', '\'', '"', '\t', ' ')

    override fun pushLexem(type: LexemType, lexem: String, positionOfStart: Int) {
        var actualStart = positionOfStart
        if (actualStart == -1) {
            actualStart = currPos
        }
        tokens.add(Lexem(type, lexem, actualStart, currPos - 1))
    }

    override fun lexInput(): ArrayList<Lexem> {
        val stopLength = fedString.length
        if (stopLength == 0) {
            return tokens
        }
        var currentChar = fedString[currPos]
        var currPos_ = Integer(0)
        while (currPos <= stopLength - 1) {
            val posWas = currPos
            when (currentChar) {
                in "\'\"" -> lexQuotedWord(currentChar)
                in "=|" -> lexOperation(currentChar)
                in " \t" -> currPos++
                else -> lexWord(currentChar)
            }
            if (posWas == currPos) {
                currPos++
            }
            currentChar = lookAt()
        }

        return tokens
    }

    override fun lexWord(startsWith: Char) {
        val buf = StringBuilder()
        val startOnPosition = currPos
        var currentChar = startsWith

        while (!stopLexems.contains(currentChar)) {
            buf.append(currentChar)
            currPos++
            currentChar = lookAt()
        }

        pushLexem(LexemType.WORD, buf.toString(), startOnPosition)
    }

    override fun lexQuotedWord(startsWith: Char) {
        val buf = StringBuilder()
        val startOnPosition = currPos++
        var currentChar = lookAt()

        while (true) {
            if (currentChar == startsWith) break
            if (currentChar == '\u0000')
                throw LexException("Unexpected end of the word", currPos)
            buf.append(currentChar)
            currPos++
            currentChar = lookAt()
        }
        currPos++

        val type = if (startsWith == '"') LexemType.DOUBLE_QUOTED_W else LexemType.QUOTED_W
        pushLexem(type, buf.toString(), startOnPosition)
    }

    override fun lexOperation(startsWith: Char) {
        if (startsWith == '=') {
            pushLexem(LexemType.ASSIGN_OP, startsWith.toString())
        } else if (startsWith == '|') {
            pushLexem(LexemType.PIPE, startsWith.toString())
        }
    }

    /**
     * Allows to lookup the char at position without moving current
     *
     * @param pos representing position to look at
     * @return Char at pos or end-of-string char
     */
    override fun lookAt(pos: Int): Char {
        if (currPos + pos >= fedString.length) {
            return '\u0000'
        }
        return fedString[currPos + pos]
    }
}
