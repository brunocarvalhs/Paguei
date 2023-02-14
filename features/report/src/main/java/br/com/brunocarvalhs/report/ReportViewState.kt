package br.com.brunocarvalhs.report

sealed class ReportViewState {
    object Loading : ReportViewState()
    object Success : ReportViewState()
    data class Error(val message: String?) : ReportViewState()
}

