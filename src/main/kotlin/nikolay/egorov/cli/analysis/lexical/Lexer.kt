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

    override fun pushLexem(type: LexemType, lexem: String, positionOfStart: Int) {
        var actualStart = positionOfStart
        if (actualStart == -1) {
            actualStart = currPos
        }
        if (actualStart == currPos) {
            tokens.add(Lexem(type, lexem, actualStart, currPos))
        } else {
            tokens.add(Lexem(type, lexem, actualStart, currPos - 1))
        }
    }

    override fun lexInput(): ArrayList<Lexem> {
        val stopLength = fedString.length
        if (stopLength == 0) {
            return tokens
        }
        var currentChar = fedString[currPos]

        while (currPos <= stopLength - 1) {
            val posWas = currPos
            when (currentChar) {
                in "\'\"" -> lexQuotedWord(currentChar)
                in "=|" -> lexOperation(currentChar)
                in " \t" -> getNext()
                else -> lexWord(currentChar)
            }
            if (posWas == currPos) {
                currentChar = getNext()
            } else {
                currentChar = lookAt(0)
            }
        }

        return tokens
    }

    override fun lexWord(startsWith: Char) {
        val buf = StringBuilder()
        val startOnPosition = currPos
        var currentChar = startsWith

        val stopPredicate = { char: Char ->
            val ans = when (char) {
                '=' -> true
                '|' -> true
                '\u0000' -> true
                '\'' -> true
                '"' -> true
                '\t' -> true
                ' ' -> true
                else -> false
            }
            ans
        }

        while (!stopPredicate(currentChar)) {
            buf.append(currentChar)
            currentChar = getNext()
        }

        pushLexem(LexemType.WORD, buf.toString(), startOnPosition)
    }

    override fun lexQuotedWord(startsWith: Char) {
        val buf = StringBuilder()
        val startOnPosition = currPos
        var currentChar = getNext()

        while (true) {
            if (currentChar == startsWith) break
            if (currentChar == '\u0000')
                throw LexException("Unexpected end of the word", currPos)
            buf.append(currentChar)
            currentChar = getNext()
        }
        getNext()

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

    override fun getNext(): Char {
        currPos++
        if (currPos >= fedString.length) {
            return '\u0000'
        }
        return fedString[currPos]
    }

    /**
     * Allows to lookup the char at position without moving current
     *
     * @param pos representing position to look at
     * @return Char at pos or end-of-string char
     */
    private fun lookAt(pos: Int): Char {
        if (currPos + pos >= fedString.length) {
            return '\u0000'
        }
        return fedString[currPos + pos]
    }
}
