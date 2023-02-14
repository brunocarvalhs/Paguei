package br.com.brunocarvalhs.report

import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.data.model.CostsModel
import br.com.brunocarvalhs.domain.entities.CostsEntities
import br.com.brunocarvalhs.domain.repositories.CostsRepository
import br.com.brunocarvalhs.domain.services.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val repository: CostsRepository,
    sessionManager: SessionManager
) : BaseViewModel<ReportViewState>() {

    private var listCosts = mutableListOf<CostsEntities>()
        set(value) {
            if (!listCosts.containsAll(value)) {
                field = value
            }
        }

    var totalCosts = 0f
        private set

    var totalPay = 0f
        private set

    var totalRender = sessionManager.getUser()?.salary?.toFloat()
        private set

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

    private fun formattedDecimal(value: Double) =
        DecimalFormat(CostsModel.FORMAT_VALUE).format(value)
}