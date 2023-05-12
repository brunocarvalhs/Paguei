package br.com.brunocarvalhs.billet_registration.form

import androidx.databinding.ObservableField
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.data.model.CostsModel
import br.com.brunocarvalhs.data.utils.FORMAT_MONTH
import br.com.brunocarvalhs.domain.repositories.CostsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class BilletRegistrationFormViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: CostsRepository,
) : BaseViewModel<BilletRegistrationFormViewState>() {

    val name = ObservableField<String>()

    val prompt = ObservableField<String>()

    val value = ObservableField<String>()

    val barCode = ObservableField<String>(
        BilletRegistrationFormFragmentArgs
            .fromSavedStateHandle(savedStateHandle).barcode
    )

    val dateReferenceMonth = ObservableField(SimpleDateFormat(FORMAT_MONTH).format(Date()))

    fun saveCost() {
        viewModelScope.launch {
            try {
                mutableState.value = BilletRegistrationFormViewState.Loading
                repository.add(generateCost())
                mutableState.value = BilletRegistrationFormViewState.Success
            } catch (error: Exception) {
                mutableState.value = BilletRegistrationFormViewState.Error(error.message)
            }
        }
    }

    private fun generateCost() = CostsModel(
        name = name.get(),
        prompt = prompt.get(),
        value = value.get()?.replace("[^0-9,]".toRegex(), "")?.replace(",", "."),
        barCode = barCode.get(),
        dateReferenceMonth = dateReferenceMonth.get()
    )
}