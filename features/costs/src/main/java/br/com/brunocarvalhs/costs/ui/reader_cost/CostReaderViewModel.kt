package br.com.brunocarvalhs.costs.ui.reader_cost

import androidx.databinding.ObservableField
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.commons.utils.moneyReplace
import br.com.brunocarvalhs.data.model.CostsModel
import br.com.brunocarvalhs.domain.usecase.cost.UpdateCostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CostReaderViewModel @Inject constructor(
    private val useCase: UpdateCostUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<CostReaderViewState>() {
    val cost = CostReaderFragmentArgs.fromSavedStateHandle(savedStateHandle).cost as CostsModel

    val name = ObservableField<String>(cost.name)

    val prompt = ObservableField<String>(cost.prompt)

    val value = ObservableField(cost.formatValue())

    val barCode = ObservableField<String>(cost.barCode)

    val dateReferenceMonth = ObservableField<String>(cost.dateReferenceMonth)

    fun updateCost() {
        viewModelScope.launch {
            mutableState.value = CostReaderViewState.Loading
            useCase.invoke(generateCost()).onSuccess {
                mutableState.value = CostReaderViewState.Success(it)
            }.onFailure { error ->
                mutableState.value = CostReaderViewState.Error(error.message)
            }
        }
    }

    private fun generateCost() = cost.copy(
        name = name.get(),
        prompt = prompt.get(),
        value = value.get()?.moneyReplace(),
        barCode = barCode.get(),
        dateReferenceMonth = dateReferenceMonth.get()
    )
}