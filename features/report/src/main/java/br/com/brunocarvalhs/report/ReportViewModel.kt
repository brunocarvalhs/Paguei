package br.com.brunocarvalhs.report

import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.domain.entities.CostEntities
import br.com.brunocarvalhs.domain.repositories.CostsRepository
import br.com.brunocarvalhs.domain.services.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val repository: CostsRepository,
    sessionManager: SessionManager
) : BaseViewModel<ReportViewState>() {

    private var listCosts = mutableListOf<CostEntities>()
        set(value) {
            if (!listCosts.containsAll(value)) {
                field = value
            }
        }

    var totalCosts = 0f
        private set

    private var totalPay = 0f
        private set

    var totalRender = sessionManager.getUser()?.salary
        private set

    private var filterDate: String? = null

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

    fun selectedFilter(date: String) {
        mutableState.value = ReportViewState.Loading
        filterDate = date
        mutableState.value = ReportViewState.Success
    }

    fun defineFilters(): List<String?> {
        return listCosts
            .groupBy { convertDate(it.datePayment) }
            .map { it.key }
            .sortedBy { orderByDate(it) }
    }

    private fun convertDate(date: String?): String? {
        return date?.let {
            val formatoEntrada = SimpleDateFormat("dd/MM/yyyy")
            val formatoSaida = SimpleDateFormat("MMMM / yyyy", Locale.getDefault())
            val data = formatoEntrada.parse(date)
            data?.let { formatoSaida.format(it) }
        }
    }

    private fun orderByDate(date: String?): Date? {
        return date?.let {
            val formatoSaida = SimpleDateFormat("MMMM / yyyy", Locale.getDefault())
            formatoSaida.parse(it)
        }
    }

    fun totalCosts(): String {
        val total = listCosts.filter { it.paymentVoucher.isNullOrEmpty() }.map {
            it.value?.toDouble()
        }
        totalCosts = total.filterNotNull().sumOf { it }.toFloat()
        return formattedDecimal(totalCosts.toDouble())
    }

    fun totalPay(): String {
        val total = listCosts.filter { it.paymentVoucher != null }.map {
            it.value?.toDouble()
        }
        totalPay = total.filterNotNull().sumOf { it }.toFloat()
        return formattedDecimal(totalPay.toDouble())
    }

    private fun formattedDecimal(value: Double): String  {
        val numberFormat = NumberFormat.getInstance(Locale.getDefault())
        numberFormat.maximumFractionDigits = 2
        numberFormat.minimumFractionDigits = 2
        return numberFormat.format(value)
    }
}