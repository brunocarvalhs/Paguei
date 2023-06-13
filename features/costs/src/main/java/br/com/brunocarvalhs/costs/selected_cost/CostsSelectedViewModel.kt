package br.com.brunocarvalhs.costs.selected_cost

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.domain.usecase.cost.DeleteCostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CostsSelectedViewModel @Inject constructor(
    private val useCase: DeleteCostUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CostsSelectedViewState>() {

    val cost = CostsSelectedDialogFragmentArgs.fromSavedStateHandle(savedStateHandle).costs

    fun deleteCost() {
        viewModelScope.launch {
            mutableState.value = CostsSelectedViewState.Loading
            useCase.invoke(cost)
                .onSuccess { mutableState.value = CostsSelectedViewState.Success }
                .onFailure { error ->
                    mutableState.value = CostsSelectedViewState.Error(error.message)
                }
        }
    }
}