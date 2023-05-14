package br.com.brunocarvalhs.report

import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.domain.entities.CostEntities
import br.com.brunocarvalhs.domain.services.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ReportMonthViewModel @Inject constructor(
    sessionManager: SessionManager
) : BaseViewModel<ReportViewState>() {

    private var totalCosts = 0f
        private set

    private var totalPay = 0f
        private set

    var totalRender = sessionManager.getUser()?.salary
        private set

    fun totalCosts(list: List<CostEntities>): String {
        val total = list.filter { it.paymentVoucher.isNullOrEmpty() }.map {
            it.value?.toDouble()
        }
        totalCosts = total.filterNotNull().sumOf { it }.toFloat()
        return formattedDecimal(totalCosts.toDouble())
    }

    fun totalPay(list: List<CostEntities>): String {
        val total = list.filter { it.paymentVoucher != null }.map {
            it.value?.toDouble()
        }
        totalPay = total.filterNotNull().sumOf { it }.toFloat()
        return formattedDecimal(totalPay.toDouble())
    }

    private fun formattedDecimal(value: Double): String {
        val numberFormat = NumberFormat.getInstance(Locale.getDefault())
        numberFormat.maximumFractionDigits = 2
        numberFormat.minimumFractionDigits = 2
        return numberFormat.format(value)
    }
}