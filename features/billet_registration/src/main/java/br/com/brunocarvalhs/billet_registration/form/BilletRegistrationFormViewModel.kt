package br.com.brunocarvalhs.billet_registration.form

import androidx.databinding.ObservableField
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.commons.utils.FORMAT_MONTH
import br.com.brunocarvalhs.commons.utils.moneyReplace
import br.com.brunocarvalhs.data.model.CostsModel
import br.com.brunocarvalhs.domain.services.AnalyticsService
import br.com.brunocarvalhs.domain.usecase.cost.AddCostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class BilletRegistrationFormViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val useCase: AddCostUseCase,
    private val analyticsService: AnalyticsService,
) : BaseViewModel<BilletRegistrationFormViewState>() {

    val name = ObservableField<String>()
    val prompt = ObservableField<String>()
    val value = ObservableField<String>()
    val barCode = ObservableField<String>(
        BilletRegistrationFormFragmentArgs.fromSavedStateHandle(savedStateHandle).barcode
    )
    val dateReferenceMonth = ObservableField(SimpleDateFormat(FORMAT_MONTH).format(Date()))

    fun saveCost() {
        viewModelScope.launch {
            mutableState.value = BilletRegistrationFormViewState.Loading
            useCase.invoke(generateCost()).onSuccess {
                analyticsService.trackEvent(
                    AnalyticsService.Events.SAVE_SUCCESS,
                    mapOf(Pair("event_name", "register_cost")),
                    BilletRegistrationFormViewModel::class
                )
                mutableState.value = BilletRegistrationFormViewState.Success
            }.onFailure { error ->
                analyticsService.trackEvent(
                    AnalyticsService.Events.SAVE_FAILURE,
                    mapOf(Pair("event_name", "register_cost")),
                    BilletRegistrationFormViewModel::class
                )
                mutableState.value = BilletRegistrationFormViewState.Error(error.message)
            }
        }
    }

    private fun generateCost() = CostsModel(
        name = name.get(),
        prompt = prompt.get(),
        value = value.get()?.moneyReplace(),
        barCode = barCode.get(),
        dateReferenceMonth = dateReferenceMonth.get()
    )
}