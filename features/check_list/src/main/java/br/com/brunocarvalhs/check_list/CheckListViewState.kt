package br.com.brunocarvalhs.check_list

sealed class CheckListViewState {
    data class Success(val list: HashMap<String, Map<String?, Boolean>>) : CheckListViewState()
    data class Error(val message: String?) : CheckListViewState()
    object Loading : CheckListViewState()
}
