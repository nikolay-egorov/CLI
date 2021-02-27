package nikolay.egorov.cli.analysis.exceptions

/**
 * Exception represents unexpected read of raw input state
 */
class LexException(message: String, position: Int) : Exception("[$position] $message")
