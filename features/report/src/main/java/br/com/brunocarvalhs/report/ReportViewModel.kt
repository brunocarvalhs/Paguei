package br.com.brunocarvalhs.report

import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
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

    private var listCosts = mutableListOf<CostEntities>()
        set(value) {
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
            .groupBy { convertDate(it.datePayment) }
            .mapValues { (_, values) -> values.sortedByDescending { it.datePayment } }
            .toSortedMap(compareByDescending { it })
            .toMutableMap()
    }

    private fun convertDate(date: String?): String? {
        return date?.let {
            val formatoEntrada = SimpleDateFormat("dd/MM/yyyy")
            val formatoSaida = SimpleDateFormat("MMMM / yyyy", Locale.getDefault())
            val data = formatoEntrada.parse(date)
            data?.let { formatoSaida.format(it) }
        }
    }
}