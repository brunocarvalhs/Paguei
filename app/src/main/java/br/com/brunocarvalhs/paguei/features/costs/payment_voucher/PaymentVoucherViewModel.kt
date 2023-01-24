package br.com.brunocarvalhs.paguei.features.costs.payment_voucher

import androidx.databinding.ObservableField
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.data.model.CostsModel
import br.com.brunocarvalhs.domain.repositories.CostsRepository
import br.com.brunocarvalhs.paguei.features.costs.reader_cost.CostReaderFragmentArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentVoucherViewModel @Inject constructor(
    private val repository: CostsRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<PaymentVoucherViewState>() {

    val cost = CostReaderFragmentArgs.fromSavedStateHandle(savedStateHandle).cost as CostsModel

    val name = ObservableField<String>(cost.name)

    val prompt = ObservableField<String>(cost.prompt)

    val value = ObservableField(cost.formatValue())

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

    private fun generateCost() = cost.copy(
        paymentVoucher = paymentVoucherUri.get()
    )
}