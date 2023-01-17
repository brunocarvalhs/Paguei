package br.com.brunocarvalhs.payflow.features.billet_registration.form

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.payflow.domain.entities.CostsEntities
import br.com.brunocarvalhs.payflow.domain.repositories.CostsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class BilletRegistrationFormViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: CostsRepository,
) : BaseViewModel<BilletRegistrationFormViewState>() {

    private var _name: String? = null
    val name: String? = _name

    private var _prompt: Date? = null
    val prompt: Date? = _prompt

    private var _value: Double? = null
    val value: Double? = _value

    private var _barCode: String? = BilletRegistrationFormFragmentArgs
        .fromSavedStateHandle(savedStateHandle).barcode
    val barCode: String? = _barCode

    fun saveCost(costs: CostsEntities) {
        viewModelScope.launch {
            try {
                mutableState.value = BilletRegistrationFormViewState.Loading
                repository.add(costs)
                mutableState.value = BilletRegistrationFormViewState.Success
            } catch (error: Exception) {
                mutableState.value = BilletRegistrationFormViewState.Error(error.message)
            }
        }
    }
}