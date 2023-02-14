package br.com.brunocarvalhs.paguei.features.costs.extract_reader

sealed class ExtractReaderViewState {
    object Loading : ExtractReaderViewState()
    object Success : ExtractReaderViewState()
    data class Error(val error: String?) : ExtractReaderViewState()
}
