package br.com.brunocarvalhs.payflow.features.costs.extract_reader

sealed class ExtractReaderViewState {
    object Loading : ExtractReaderViewState()
    object Success : ExtractReaderViewState()
    data class Error(val error: String?) : ExtractReaderViewState()
}
