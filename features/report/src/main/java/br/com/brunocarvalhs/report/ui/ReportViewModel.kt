package br.com.brunocarvalhs.report.ui

import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseComposeViewModel
import br.com.brunocarvalhs.commons.utils.FORMAT_MONTH
import br.com.brunocarvalhs.commons.utils.FORMAT_MONTH_NAME
import br.com.brunocarvalhs.domain.entities.CostEntities
import br.com.brunocarvalhs.domain.repositories.CostsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val repository: CostsRepository,
) : BaseComposeViewModel<ReportViewState>(ReportViewState.Loading) {

    private var listCosts = mutableListOf<CostEntities>()
        private set(value) {
            if (!listCosts.containsAll(value)) {
                field = value
            }
        }

    private var currentDate = Date()
        set(value) {
            field = value
            fetchData(date = value.toFormatDate())
        }

    fun onNextMonth() {
        currentDate = Calendar.getInstance().apply {
            time = currentDate
            add(Calendar.MONTH, 1)
        }.time
    }

    fun onPreviousMonth() {
        currentDate = Calendar.getInstance().apply {
            time = currentDate
            add(Calendar.MONTH, -1)
        }.time
    }

    fun fetchData(date: String? = null) {
        viewModelScope.launch {
            try {
                mutableState.value = ReportViewState.Loading
                listCosts = repository.list().toMutableList()
                mutableState.value = ReportViewState.Success(
                    list = defineFilters(date),
                    date = convertDate(date = date ?: currentDate.toFormatDate()).orEmpty()
                )
            } catch (error: Exception) {
                mutableState.value = ReportViewState.Error(error.message)
            }
        }
    }

    private fun defineFilters(date: String? = null): MutableMap<String?, List<CostEntities>> {
        return listCosts.filter { filter ->
            date?.let { filter.dateReferenceMonth == date } ?: true
        }.groupBy { convertDate(it.dateReferenceMonth) }
            .mapValues { (_, values) -> values.sortedByDescending { it.dateReferenceMonth } }
            .toSortedMap(compareByDescending { it }).toMutableMap()
    }

    private fun convertDate(date: String?): String? {
        return date?.let {
            val formatoEntrada = SimpleDateFormat(FORMAT_MONTH)
            val formatoSaida = SimpleDateFormat(FORMAT_MONTH_NAME, Locale.getDefault())
            val data = formatoEntrada.parse(date)
            data?.let { formatoSaida.format(it) }
        }
    }

    private fun Date.toFormatDate(): String {
        return SimpleDateFormat(FORMAT_MONTH).format(this)
    }
}