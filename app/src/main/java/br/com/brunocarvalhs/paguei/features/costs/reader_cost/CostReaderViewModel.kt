package br.com.brunocarvalhs.paguei.features.costs.reader_cost

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.data.model.CostsModel
import br.com.brunocarvalhs.domain.repositories.CostsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CostReaderViewModel @Inject constructor(
    private val repository: CostsRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<CostReaderViewState>() {
    val cost = CostReaderFragmentArgs.fromSavedStateHandle(savedStateHandle).cost

    fun updateCost(cost: CostsModel) {
        viewModelScope.launch {
            try {
                mutableState.value = CostReaderViewState.Loading
                val update = repository.update(cost)
                mutableState.value = CostReaderViewState.Success(update)
            } catch (error: Exception) {
                mutableState.value = CostReaderViewState.Error(error.message)
            }
        }
    }
}