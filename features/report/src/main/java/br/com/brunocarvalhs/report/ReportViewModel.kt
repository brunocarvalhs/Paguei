package br.com.brunocarvalhs.report

import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.commons.utils.orEmpty
import br.com.brunocarvalhs.domain.entities.CostEntities
import br.com.brunocarvalhs.domain.repositories.CostsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val repository: CostsRepository,
) : BaseViewModel<ReportViewState>() {

    var listCosts = mutableListOf<CostEntities>()
        private set(value) {
            if (!listCosts.containsAll(value)) {
                field = value
            }
        }

    fun fetchData() {
        viewModelScope.launch {
            try {
                mutableState.value = ReportViewState.Loading
                listCosts = repository.list().toMutableList()
                mutableState.value = ReportViewState.Success
            } catch (error: Exception) {
                mutableState.value = ReportViewState.Error(error.message)
            }
        }
    }

    fun defineFilters(): MutableMap<String?, List<CostEntities>> {
        return listCosts
            .groupBy { convertDate(it.dateReferenceMonth) }
            .mapValues { (_, values) -> values.sortedByDescending { it.dateReferenceMonth } }
            .toSortedMap(compareByDescending { it })
            .toMutableMap()
    }

    fun defineBarChart(): List<MonthData> {
        val result = listCosts
            .groupBy { convertDate(it.dateReferenceMonth) }
            .map { (date, costs) ->
                val totalValue = costs.sumOf { it.value?.toDouble().orEmpty() }
                MonthData(date = date, totalValue = totalValue)
            }
            .sortedByDescending { it.date }

        return result
    }

    data class MonthData(
        val date: String?,
        val totalValue: Double
    )

    private fun convertDate(date: String?): String? {
        return date?.let {
            val formatoEntrada = SimpleDateFormat("MM/yyyy")
            val formatoSaida = SimpleDateFormat("MMMM/yy", Locale.getDefault())
            val data = formatoEntrada.parse(date)
            data?.let { formatoSaida.format(it) }
        }
    }
}