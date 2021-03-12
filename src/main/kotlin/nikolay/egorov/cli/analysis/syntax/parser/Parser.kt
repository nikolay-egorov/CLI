package nikolay.egorov.cli.analysis.syntax.parser

import nikolay.egorov.cli.analysis.exceptions.ParserException
import nikolay.egorov.cli.analysis.lexical.Lexem
import nikolay.egorov.cli.analysis.lexical.LexemType
import nikolay.egorov.cli.analysis.syntax.representation.AssignStatement
import nikolay.egorov.cli.analysis.syntax.representation.CommandChain
import nikolay.egorov.cli.analysis.syntax.representation.CommandStatement
import nikolay.egorov.cli.analysis.syntax.representation.Statement

/**
 * Base class for syntax analysis phase
 * @see ParserInterface
 */
class Parser(input: ArrayList<Lexem>) : ParserInterface {

    /**
     * Helper class for parsing
     * Mirrors ParserInterface to match to its' calls
     */
    class ParserUtil(input: ArrayList<Lexem>) {
        private val stopLexem: Lexem = Lexem(LexemType.EOF, "", -1, -1)

        private val tokens: List<Lexem> = input
        private val size = tokens.size
        private var position = 0

        operator fun get(relativePosition: Int): Lexem {
            val position: Int = position + relativePosition
            return if (position >= size) stopLexem else tokens[position]
        }

        fun match(comparingType: LexemType): Boolean {
            val matched = get(0).type == comparingType
            if (matched) position++
            return matched
        }

        private fun matchAnyOf(vararg types: LexemType): Boolean {
            return listOf(types).any {
                it.any(::match)
            }
        }

        fun advance(): Lexem {
            val lexem = get(0)
            position++
            return lexem
        }

        /**
         * Creates command statement based on underling input
         * @see CommandStatement
         * @return CommandStatement
         */
        fun createCommandStatement(): Statement {
            val first = get(0)
            if (!matchAnyOf(LexemType.WORD, LexemType.QUOTED_W, LexemType.DOUBLE_QUOTED_W))
                throw ParserException("Command's name is missing", first)

            val args = ArrayList<Lexem>()
            var atPos: Lexem
            do {
                val arg = advance()
                if (arg.type == LexemType.EOF || arg.type == LexemType.PIPE) {
                    break
                }
                args.add(arg)
                atPos = get(0)
            } while (atPos.type != LexemType.EOF && atPos.type != LexemType.PIPE)
            return CommandStatement(first, args)
        }

        /**
         * Creates assign statement based on underling input
         * @see AssignStatement
         * @return AssignStatement
         */
        fun createAssignmentStatement(): Statement {
            val leftSide = advance()
            match(LexemType.ASSIGN_OP)
            val rightSide = ArrayList<Lexem>()
            var atPos: Lexem
            do {
                val arg = advance()
                if (arg.type != LexemType.EOF) {
                    rightSide.add(arg)
                }
                atPos = get(0)
            } while (atPos.type != LexemType.EOF && atPos.type != LexemType.PIPE)

            return AssignStatement(leftSide, rightSide)
        }

        /**
         * Checks if underlying statement can be represented as an assign
         * @return Boolean
         */
        fun isNextAssign(): Boolean {
            var typeAhead = get(0).type
            val firstIsWord = LexemType.WORD == typeAhead
                    || LexemType.QUOTED_W == typeAhead || LexemType.DOUBLE_QUOTED_W == typeAhead
            typeAhead = get(1).type
            val noSpaceBetween = get(0).endInd + 1 == get(1).startInd

            return firstIsWord && (typeAhead == LexemType.ASSIGN_OP) && noSpaceBetween
        }
    }

    private val parserHelper: ParserUtil = ParserUtil(input)

    override fun parse(): Statement {
        val res = ArrayList<Statement>()

        if (match(LexemType.EOF)) return CommandChain(res)
        if (!match(LexemType.PIPE)) {
            if (parserHelper.isNextAssign()) {
                res.add(parserHelper.createAssignmentStatement())
            } else {
                res.add(parserHelper.createCommandStatement())
            }
        }

        while (match(LexemType.PIPE) || !match(LexemType.EOF)) {
            if (parserHelper.isNextAssign()) {
                res.add(parserHelper.createAssignmentStatement())
            } else {
                res.add(parserHelper.createCommandStatement())
            }
        }

        return CommandChain(res)
    }

    override fun match(type: LexemType): Boolean {
        return parserHelper.match(type)
    }

    override fun advance(): Lexem {
        return parserHelper.advance()
    }

    override fun getAt(position: Int): Lexem {
        return parserHelper[position]
    }

    /**
     * This is a mirrored method of a private method for test purposes
     */
    fun isNextAssignForTest(): Boolean {
        return parserHelper.isNextAssign()
    }
}
