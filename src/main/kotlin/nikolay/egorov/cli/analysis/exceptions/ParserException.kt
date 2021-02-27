package nikolay.egorov.cli.analysis.exceptions

import nikolay.egorov.cli.analysis.lexical.Lexem

/**
 * Exception represents unexpected read of lexed input state
 */
class ParserException(message: String, lexem: Lexem?) : Exception("[$lexem] $message")