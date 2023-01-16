package br.com.brunocarvalhs.payflow.features.costs.selected_cost

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.payflow.domain.repositories.CostsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CostsSelectedViewModel @Inject constructor(
    private val repository: CostsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val cost = CostsSelectedDialogFragmentArgs.fromSavedStateHandle(savedStateHandle).costs

    fun deleteCost() {
        viewModelScope.launch {
            repository.delete(cost)
        }
    }
}