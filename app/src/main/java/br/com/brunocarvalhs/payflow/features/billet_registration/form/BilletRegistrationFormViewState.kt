package br.com.brunocarvalhs.payflow.features.billet_registration.form

sealed class BilletRegistrationFormViewState {
    object Loading : BilletRegistrationFormViewState()
    object Success : BilletRegistrationFormViewState()
    data class Error(val error: String?) : BilletRegistrationFormViewState()
}
