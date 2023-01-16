package br.com.brunocarvalhs.payflow.features.costs.reader_cost

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import br.com.brunocarvalhs.payflow.domain.repositories.CostsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CostReaderViewModel @Inject constructor(
    private val repository: CostsRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val cost = CostReaderFragmentArgs.fromSavedStateHandle(savedStateHandle).cost
}