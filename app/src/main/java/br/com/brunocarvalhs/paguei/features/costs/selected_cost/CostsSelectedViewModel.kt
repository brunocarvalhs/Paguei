package br.com.brunocarvalhs.paguei.features.costs.selected_cost

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.domain.repositories.CostsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CostsSelectedViewModel @Inject constructor(
    private val repository: CostsRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CostsSelectedViewState>() {

    val cost = CostsSelectedDialogFragmentArgs.fromSavedStateHandle(savedStateHandle).costs

    fun deleteCost() {
        viewModelScope.launch {
            try {
                mutableState.value = CostsSelectedViewState.Loading
                repository.delete(cost)
                mutableState.value = CostsSelectedViewState.Success
            } catch (error: Exception) {
                mutableState.value = CostsSelectedViewState.Error(error.message)
            }
        }
    }
}