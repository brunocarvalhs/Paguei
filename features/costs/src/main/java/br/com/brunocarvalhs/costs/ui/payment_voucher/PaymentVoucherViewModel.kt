package br.com.brunocarvalhs.costs.ui.payment_voucher

import androidx.databinding.ObservableField
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.commons.utils.FORMAT_DATE
import br.com.brunocarvalhs.data.model.CostsModel
import br.com.brunocarvalhs.domain.entities.CostEntities
import br.com.brunocarvalhs.domain.usecase.cost.UpdateCostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class PaymentVoucherViewModel @Inject constructor(
    private val useCase: UpdateCostUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<PaymentVoucherViewState>() {

    val cost: CostEntities = PaymentVoucherFragmentArgs.fromSavedStateHandle(savedStateHandle).cost

    val name = ObservableField<String>(cost.name)

    val referenceMonth = ObservableField<String>(cost.dateReferenceMonth)

    val prompt = ObservableField<String>(cost.prompt)

    val value = ObservableField(cost.formatValue())

    val datePayment = ObservableField(SimpleDateFormat(FORMAT_DATE).format(Date()))

    val barCode = ObservableField<String>(cost.barCode)

    val paymentVoucherUri = ObservableField<String>()

    fun savePaymentVoucher() {
        viewModelScope.launch {
            mutableState.value = PaymentVoucherViewState.Loading
            useCase.invoke(generateCost()).onSuccess {
                mutableState.value = PaymentVoucherViewState.Success(it)
            }.onFailure { error ->
                mutableState.value = PaymentVoucherViewState.Error(error.message)
            }
        }
    }

    private fun generateCost() = (cost as CostsModel).copy(
        paymentVoucher = paymentVoucherUri.get(),
        datePayment = datePayment.get()
    )
}