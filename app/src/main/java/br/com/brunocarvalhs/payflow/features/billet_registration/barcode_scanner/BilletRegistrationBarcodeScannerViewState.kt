package br.com.brunocarvalhs.payflow.features.billet_registration.barcode_scanner

sealed class BilletRegistrationBarcodeScannerViewState {
    object Loading : BilletRegistrationBarcodeScannerViewState()
    data class Success(val code: String) : BilletRegistrationBarcodeScannerViewState()
    data class Error(val message: String?) : BilletRegistrationBarcodeScannerViewState()
}