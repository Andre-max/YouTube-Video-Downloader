package com.github.kotvertolet.youtubejextractor.exception

class ExtractionException : Exception {
    constructor(message: String) : super(ERROR_MESSAGE + message) {}
    constructor(message: String, cause: Throwable?) : super(ERROR_MESSAGE + message, cause) {}
    constructor(cause: Throwable?) : super(cause) {}

    companion object {
        private const val ERROR_MESSAGE =
            "Extraction failed. Please, report here: https://github.com/kotvertolet/youtube-jextractor/issues. Error details: "
    }
}