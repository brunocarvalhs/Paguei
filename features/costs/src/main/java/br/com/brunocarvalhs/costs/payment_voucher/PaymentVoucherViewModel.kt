package br.com.brunocarvalhs.costs.payment_voucher

import androidx.databinding.ObservableField
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.commons.utils.FORMAT_DATE
import br.com.brunocarvalhs.data.model.CostsModel
import br.com.brunocarvalhs.domain.entities.CostEntities
import br.com.brunocarvalhs.domain.repositories.CostsRepository
import br.com.brunocarvalhs.paguei.features.costs.payment_voucher.PaymentVoucherViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class PaymentVoucherViewModel @Inject constructor(
    private val repository: CostsRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<PaymentVoucherViewState>() {

    val cost: CostEntities = PaymentVoucherFragmentArgs.fromSavedStateHandle(savedStateHandle).cost

    val name = ObservableField<String>(cost.name)

    val prompt = ObservableField<String>(cost.prompt)

    val value = ObservableField(cost.formatValue())

    val datePayment = ObservableField(SimpleDateFormat(FORMAT_DATE).format(Date()))

    val barCode = ObservableField<String>(cost.barCode)

    val paymentVoucherUri = ObservableField<String>()

    fun savePaymentVoucher() {
        viewModelScope.launch {
            try {
                mutableState.value = PaymentVoucherViewState.Loading
                val data = repository.update(generateCost())
                mutableState.value = PaymentVoucherViewState.Success(data)
            } catch (error: Exception) {
                mutableState.value = PaymentVoucherViewState.Error(error.message)
            }
        }
    }

    private fun generateCost() = (cost as CostsModel).copy(
        paymentVoucher = paymentVoucherUri.get(),
        datePayment = datePayment.get()
    )
}