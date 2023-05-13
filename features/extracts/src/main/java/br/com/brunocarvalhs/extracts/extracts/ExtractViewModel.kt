package br.com.brunocarvalhs.extracts.extracts

import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.domain.entities.CostEntities
import br.com.brunocarvalhs.domain.usecase.cost.FetchExtractsCostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExtractViewModel @Inject constructor(
    private val useCase: FetchExtractsCostsUseCase,
) : BaseViewModel<ExtractViewState>() {

    private var listCosts = mutableListOf<CostEntities>()
        set(value) {
            if (!listCosts.containsAll(value)) {
                field = value
            }
        }

    fun fetchData() {
        viewModelScope.launch {
            mutableState.value = ExtractViewState.Loading
            useCase.invoke().onSuccess {
                listCosts = it.toMutableList()
                mutableState.value = ExtractViewState.Success(listCosts)
            }.onFailure { error -> mutableState.value = ExtractViewState.Error(error.message) }
        }
    }
}
