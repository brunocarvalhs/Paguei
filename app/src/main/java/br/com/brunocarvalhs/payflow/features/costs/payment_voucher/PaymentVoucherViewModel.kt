package br.com.brunocarvalhs.payflow.features.costs.payment_voucher

import androidx.lifecycle.ViewModel
import br.com.brunocarvalhs.payflow.domain.repositories.CostsRepository
import br.com.brunocarvalhs.payflow.domain.services.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PaymentVoucherViewModel @Inject constructor(
    private val costsRepository: CostsRepository,
    private val sessionManager: SessionManager,
) : ViewModel() {

}