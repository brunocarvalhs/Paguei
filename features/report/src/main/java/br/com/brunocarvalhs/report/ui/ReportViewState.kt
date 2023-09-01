package br.com.brunocarvalhs.report.ui

import br.com.brunocarvalhs.domain.entities.CostEntities

sealed class ReportViewState {
    object Loading : ReportViewState()
    data class Success(
        val date: String,
        val list: MutableMap<String?, List<CostEntities>>
    ) : ReportViewState()
    data class Error(val message: String?) : ReportViewState()
}

