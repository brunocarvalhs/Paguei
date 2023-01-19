package br.com.brunocarvalhs.paguei.features.costs.payment_voucher

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.domain.entities.CostsEntities
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

    val cost = CostReaderFragmentArgs.fromSavedStateHandle(savedStateHandle).cost

    val paymentVoucherUri: String? = null

    fun save(cost: CostsEntities) {
        viewModelScope.launch {
            try {
                mutableState.value = PaymentVoucherViewState.Loading
                val data = repository.update(cost)
                mutableState.value = PaymentVoucherViewState.Success(data)
            } catch (error: Exception) {
                mutableState.value = PaymentVoucherViewState.Error(error.message)
            }
        }
    }
}