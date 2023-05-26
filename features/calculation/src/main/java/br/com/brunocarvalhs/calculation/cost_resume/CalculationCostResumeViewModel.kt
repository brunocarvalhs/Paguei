package br.com.brunocarvalhs.calculation.cost_resume

import androidx.databinding.ObservableField
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.data.model.CostsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculationCostResumeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CalculationCostResumeViewState>() {

    val listMembers =
        CalculationCostResumeFragmentArgs.fromSavedStateHandle(savedStateHandle).members

    val totalSalary =
        CalculationCostResumeFragmentArgs.fromSavedStateHandle(savedStateHandle).totalSalary

    private val list =
        CalculationCostResumeFragmentArgs.fromSavedStateHandle(savedStateHandle).costs

    var totalCosts = ObservableField<String>()

    fun fetchData() {
        viewModelScope.launch {
            try {
                mutableState.value = CalculationCostResumeViewState.Loading
                val result = list.map { CostsModel.fromJson(it) }
                    .filter { it.datePayment.isNullOrEmpty() }
                totalCosts.set(result.sumOf { it.value?.toDouble() ?: 0.0 }.toString())
                mutableState.value = CalculationCostResumeViewState.Success(result)
            } catch (e: Exception) {
                mutableState.value = CalculationCostResumeViewState.Error(e.message)
            }
        }
    }
}