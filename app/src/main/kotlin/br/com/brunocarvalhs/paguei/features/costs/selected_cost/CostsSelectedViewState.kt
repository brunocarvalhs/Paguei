package br.com.brunocarvalhs.paguei.features.costs.selected_cost

sealed class CostsSelectedViewState {
    object Loading : CostsSelectedViewState()
    object Success : CostsSelectedViewState()
    data class Error(val message: String?) : CostsSelectedViewState()
}
