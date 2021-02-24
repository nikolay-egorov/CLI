package nikolay.egorov.cli.analysis.lexical

/**
 * All types which Lexem can semantically relate to
 */
enum class LexemType {
    WORD,
    ASSIGN_OP,
    QUOTED_W,
    DOUBLE_QUOTED_W,
    PIPE,
    EOF
}

/**
 * Object lexem representation
 *
 * @param type its' type
 * @param text holding text representation
 * @param startInd - from
 * @param endInd - to inclusive
 */
data class Lexem(
    var type: LexemType, var text: String,
    val startInd: Int, val endInd: Int
)

