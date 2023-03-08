package br.com.brunocarvalhs.paguei.features.costs.payment_voucher

import br.com.brunocarvalhs.domain.entities.CostEntities

sealed class PaymentVoucherViewState {
    object Loading : PaymentVoucherViewState()
    data class Success(val cost: CostEntities) : PaymentVoucherViewState()
    data class Error(val error: String?) : PaymentVoucherViewState()
}
