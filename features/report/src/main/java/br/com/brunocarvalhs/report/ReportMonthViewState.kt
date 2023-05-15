package br.com.brunocarvalhs.report

sealed class ReportMonthViewState {
    object Loading : ReportMonthViewState()
    object Success : ReportMonthViewState()
    data class Error(val message: String?) : ReportMonthViewState()
}

