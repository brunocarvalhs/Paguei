package br.com.brunocarvalhs.payflow.features.costs.payment_voucher

import br.com.brunocarvalhs.payflow.domain.entities.CostsEntities

sealed class PaymentVoucherViewState {
    object Loading : PaymentVoucherViewState()
    data class Success(val cost: CostsEntities) : PaymentVoucherViewState()
    data class Error(val error: String?) : PaymentVoucherViewState()
}
